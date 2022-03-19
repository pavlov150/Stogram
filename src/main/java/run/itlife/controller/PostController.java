package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import run.itlife.dto.PostDto;
import run.itlife.service.PostService;
import run.itlife.service.TagService;
import run.itlife.service.UserService;

import javax.servlet.ServletContext;

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

    @GetMapping
    public String index(ModelMap modelMap) {
        modelMap.put("posts", postService.listAllPosts());
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

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("tags", tagService.findAll());
        modelMap.put("users", userService.findAll());
        modelMap.put("contextPath", context.getContextPath());
    }
}
