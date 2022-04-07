package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.PostDto;
import run.itlife.service.PostService;
import run.itlife.service.TagService;
import run.itlife.service.UserService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//Контроллер для постов (создание, редактирование, удаление)
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final TagService tagService;
    private final ServletContext context;

    @Autowired
    ServletContext servletContext;

    @Autowired
    public PostController(PostService postsService, UserService userService, TagService tagService, ServletContext context) {
        this.postService = postsService;
        this.userService = userService;
        this.tagService = tagService;
        this.context = context;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap) {
        // TODO сделать выборку тегов для каждого поста
       // modelMap.put("tags", tagService.findAll());
        modelMap.put("posts", postService.listAllPosts());
        modelMap.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        modelMap.put("title", "All posts");
        return "posts";
    }

    @GetMapping("/posts_detail")
    public String posts_detail(ModelMap modelMap) {
        modelMap.put("posts", postService.listAllPosts());
        modelMap.put("tags", tagService.findAll()); // TODO доработать вывод тегов по конкретному посту
        modelMap.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        modelMap.put("title", "All posts");
        return "posts-detail";
    }

    @GetMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "post-new";
    }

    @PostMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(PostDto postDto, @RequestParam("file") MultipartFile file) {

        String name = null;
        if (!file.isEmpty()) {
            try {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String filename = encoder.encode(file.getOriginalFilename()).substring(8, 15) + ".jpg";
                postDto.setPhoto(filename);
                postService.createPost(postDto);
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();

                // TODO сохранение фото в посте (ссылки на фото) и вывод фото
                // TODO проанализировать код ниже и улучшить
                byte[] bytes = file.getBytes();
                name = filename;
                final String rootPath = context.getRealPath("/resources/img/" + username); // getRealPath попробовать getContextPath
                File dir = new File(rootPath + File.separator);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
               // return "You successfully uploaded file=" + name + " in " + rootPath;
                return "redirect:/";
            } catch (Exception e) {
                return "error";
                //return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
          //  return "You failed to upload " + name + " because the file was empty.";
            return "error";
        }

    }

    @GetMapping("/post/{postId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(ModelMap modelMap, @PathVariable long postId) {
        postService.checkAuthority(postId);
        modelMap.put("post", postService.getAsDto(postId));
        setCommonParams(modelMap);
        return "post-edit";
    }

    @PostMapping("/post/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(PostDto postDto) {
        postService.checkAuthority(postDto.getPostId());
        postService.update(postDto);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable long id, ModelMap modelMap){
        modelMap.put("post", postService.findById(id));
        setCommonParams(modelMap);
        return "post-view";
    }

    @PostMapping("/post/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        postService.delete(id);
    }

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("tags", tagService.findAll());
        modelMap.put("users", userService.findAll());
        modelMap.put("contextPath", context.getContextPath());
    }

}