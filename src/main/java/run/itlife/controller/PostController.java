package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postsService, UserService userService) {
        this.postService = postsService;
        this.userService = userService;
    }

    @GetMapping
    public String index(ModelMap modelMap) {
        modelMap.put("posts", postService.listAllPosts());
        return "posts";
    }
}
