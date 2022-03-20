package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;
import run.itlife.dto.PostDto;
import run.itlife.service.PostService;
import run.itlife.service.TagService;
import run.itlife.service.UserService;

//Контроллер для постов (создание, редактирование, удаление)
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final TagService tagService;
    private final ServletContext context;

    @Autowired
    public PostController(PostService postsService, UserService userService, TagService tagService, ServletContext context) {
        this.postService = postsService;
        this.userService = userService;
        this.tagService = tagService;
        this.context = context;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.put("posts", postService.listAllPosts());
        modelMap.put("title", "All posts");
        return "posts";
    }

    @GetMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "post-new";
    }

    @PostMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(PostDto postDto) {
        postService.createPost(postDto);
        return "redirect:/";
    }

    @GetMapping("/post/{postId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(ModelMap modelMap,
                           @PathVariable long postId) {
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
    public String post(@PathVariable long id,
                       ModelMap modelMap){
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
