package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.service.PostService;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postsService) {
        this.postService = postsService;
    }

    @GetMapping
    public String index(ModelMap modelMap) {
        modelMap.put("posts", postService.listAllPosts());
        return "posts";
    }
}
