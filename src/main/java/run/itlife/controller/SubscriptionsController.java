package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import run.itlife.repository.SubscriptionsRepository;
import run.itlife.service.PostService;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;

import static run.itlife.utils.SecurityUtils.getCurrentUserDetails;

@Controller
public class SubscriptionsController {

    private final UserService userService;
    private final PostService postService;
    private final SubscriptionsService subscriptionsService;


    @Autowired
    public SubscriptionsController(UserService userService, PostService postService, SubscriptionsService subscriptionsService) {
        this.userService = userService;
        this.postService = postService;
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/sub-posts/{user}")
    @PreAuthorize("hasRole('USER')")
    public String index(ModelMap modelMap, @PathVariable String user) {
        modelMap.put("userinfo", userService.findByUsername(user));
        modelMap.put("posts", postService.sortedPostsByDate(user));
        modelMap.put("user", user);
       // modelMap.put("userinfo", userService.findByUsername(user));
        modelMap.put("countPosts", postService.countPosts(user));
        modelMap.put("isSub", subscriptionsService.isSubscribe(SecurityContextHolder.getContext().getAuthentication().getName(), user));
       // modelMap.put("unsub", subscriptionsService.searchForUnSubscribes(SecurityContextHolder.getContext().getAuthentication().getName(), user));

        return "sub-posts";
    }

    @GetMapping("/subscription/{user}")
    @PreAuthorize("hasRole('USER')")
    public String subscribe(@PathVariable String user){
        subscriptionsService.createSub(user);
        return "redirect:/sub-posts/{user}";
    }


    @GetMapping("/unsubscription/{user}")
    @PreAuthorize("hasRole('USER')")
    public String unsubscribe(@PathVariable String user){
       // String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        long subUserId = userService.findByUsername(user).getUserId().longValue();
        subscriptionsService.deleteSubscribeLong(currentUserId, subUserId);
        return "redirect:/sub-posts/{user}";
    }

}
