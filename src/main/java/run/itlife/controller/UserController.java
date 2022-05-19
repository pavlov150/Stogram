package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import run.itlife.enums.Sex;
import run.itlife.repository.UserRepository;
import run.itlife.service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static run.itlife.utils.EditImage.cropImage;
import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;

//UserController, отвечающий за логин юзеров и т.д.
//Создаем в папке view страницу register.html. Далее необходимо сделать, чтобы мы пересылали данные в контроллер.
//У UserController будет страница по которой будет идти регистрация. Для этого нужно сделать форму и она уже будет
//идти на контроллер для регистрации
@Controller
public class UserController {

    private final UserService userService;
    private final ServletContext context;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, ServletContext context, UserRepository userRepository) {
        this.userService = userService;
        this.context = context;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap){
        return "login";
    }

    @PostMapping("/register")
    public String register(User user){
        userService.create(user);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap){
        return "register";
    }

    @GetMapping("/profile_edit/{user}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String profile_edit(ModelMap modelMap, @PathVariable String user){
        modelMap.put("userinfo", userService.findByUsername(user));
        modelMap.put("sex_male", Sex.MALE);
        modelMap.put("sex_female", Sex.FEMALE);
        return "profile-edit";
    }

    @PostMapping("/profile_edit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String profile_edit(UserDto userDto, @RequestParam("file") MultipartFile file) {

       if (!file.isEmpty()) {
            try {
                // изменение и генерация ноового имени файла
                String filename = generateFileName() + ".jpg";

                // получаем имя юзера для формирования пути сохранения фото
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();

                // получение имени фото и сохранение имени фото и данных поста в БД
                userService.checkAuthority(userDto.getUserId());
                userDto.setPhoto(filename);
                userService.update(userDto);

                // сохранение самого файла в папку юзера
                File dir = new File(context.getRealPath("/resources/img/users/" + username + "/profile/"));
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
           userService.checkAuthority(userDto.getUserId());
           userService.update(userDto);
           return "redirect:/";
        }
    }


    @GetMapping("/users")
    public String index(ModelMap modelMap) {
     //  modelMap.put("posts", postService.findByUser(SecurityContextHolder.getContext().getAuthentication().getName()));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        modelMap.put("userinfo", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "users-list";
    }

}