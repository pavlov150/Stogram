package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.PostDto;
import run.itlife.repository.PostRepository;
import run.itlife.repository.UserRepository;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

import java.awt.image.BufferedImage;
import java.io.File;

import static run.itlife.utils.EditImage.cropImage;
import static run.itlife.utils.EditImage.resizeImage;

//Контроллер для постов (создание, редактирование, удаление)
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final ServletContext context;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    ServletContext servletContext;

    @Autowired
    public PostController(PostService postsService, UserService userService, ServletContext context, UserRepository userRepository, PostRepository postRepository) {
        this.postService = postsService;
        this.userService = userService;
        this.context = context;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.put("posts", postService.findByUser(SecurityContextHolder.getContext().getAuthentication().getName()));
        modelMap.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        modelMap.put("userinfo", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        modelMap.put("countPosts", postService.countPosts(SecurityContextHolder.getContext().getAuthentication().getName()));

        //modelMap.put("test", userRepository.findById(4L).orElseThrow().getUsername());
        return "posts";
    }

    //@RequestMapping(value = "/posts_detail", method = RequestMethod.GET)
    @GetMapping("/posts_detail") // такая же запись как и выше, но в другом виде, более современная
    public String posts_detail(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.findByUserName(username));
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "posts-detail";
    }

    @GetMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "post-new";
    }

   /* @GetMapping("/fragments")
    public String view_header(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "fragments";
    }*/ //TODO разобраться как вывести иконку в хедере, при редактировании профиля ошибки не было а в post-new и edit ругалось на photo

    @PostMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(PostDto postDto, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                // изменение и генерация ноового имени файла
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String filename = encoder.encode(file.getOriginalFilename()).substring(8, 15) + ".jpg"; // TODO переделать имя тк функция генерит слэши и из-за этого ошибка при сохранении фото

                // получаем имя юзера для формирования пути сохранения фото
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();

                // получение имени фото и сохранение имени фото и данных поста в БД
                postDto.setPhoto(filename);
                postService.createPost(postDto);

                // сохранение самого файла в папку юзера
                File dir = new File(context.getRealPath("/resources/img/users/" + username));
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Обрезаем изображение
                BufferedImage cropImage = null;
                BufferedImage originalImage = ImageIO.read(file.getInputStream());
                cropImage = cropImage(originalImage);

                // Уменьшаем или увеличиваем размер до 500
                BufferedImage resizeImage = null;
                File newFileJPG = null;
                resizeImage = resizeImage(cropImage, 500, 500);
                newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);
                ImageIO.write(resizeImage, "jpg", newFileJPG);

                return "redirect:/";
            } catch (Exception e) {
                return "error";
            }
        } else {
            return "error";
        }

    }

    @GetMapping("/post/{postId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(ModelMap modelMap, @PathVariable long postId) {
        postService.checkAuthority(postId);
        modelMap.put("post", postService.getAsDto(postId));
        modelMap.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        setCommonParams(modelMap);
        return "post-edit";
    }

    @PostMapping("/post/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(PostDto postDto, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                // изменение и генерация ноового имени файла
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String filename = encoder.encode(file.getOriginalFilename()).substring(8, 15) + ".jpg";

                // получаем имя юзера для формирования пути сохранения фото
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();

                // получение имени фото и сохранение имени фото и данных поста в БД
                postService.checkAuthority(postDto.getPostId());
                postDto.setPhoto(filename);
                postService.update(postDto);

                // сохранение самого файла в папку юзера
                File dir = new File(context.getRealPath("/resources/img/users/" + username));
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Обрезаем изображение
                BufferedImage cropImage = null;
                BufferedImage originalImage = ImageIO.read(file.getInputStream());
                cropImage = cropImage(originalImage);

                // Уменьшаем или увеличиваем размер до 500
                BufferedImage resizeImage = null;
                File newFileJPG = null;
                resizeImage = resizeImage(cropImage, 500, 500);
                newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);
                ImageIO.write(resizeImage, "jpg", newFileJPG);

                return "redirect:/";
            } catch (Exception e) {
                return "error";
            }
        } else {
            return "error";
        }
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable long id, ModelMap modelMap){
        modelMap.put("post", postService.findById(id));
        modelMap.put("countComments", postService.countComments(id));
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "post-view";
    }

    @PostMapping("/post/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        postService.delete(id);
    }

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        modelMap.put("contextPath", context.getContextPath());
    }

}