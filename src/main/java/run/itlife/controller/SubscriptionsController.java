package run.itlife.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

@Controller
public class SubscriptionsController {

    private final UserService userService;
    private final PostService postService;

    public SubscriptionsController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/sub-posts/{user}")
    @PreAuthorize("hasRole('USER')")
    public String index(ModelMap modelMap, @PathVariable String user) {
        modelMap.put("userinfo", userService.findByUsername(user));

        modelMap.put("posts", postService.findByUser(user));
        modelMap.put("user", user);
        modelMap.put("userinfo", userService.findByUsername(user));
        modelMap.put("countPosts", postService.countPosts(user));

        return "sub-posts";
    }
}
