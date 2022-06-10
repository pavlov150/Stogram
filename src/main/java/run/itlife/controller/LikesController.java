package run.itlife.controller;

import com.google.zxing.qrcode.decoder.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.service.LikesService;
import run.itlife.service.UserService;

import static run.itlife.utils.SecurityUtils.getCurrentUserDetails;

@Controller
public class LikesController {

    private final LikesService likesService;
    private final UserService userService;

    @Autowired
    public LikesController(LikesService likesService, UserService userService) {
        this.likesService = likesService;
        this.userService = userService;
    }


    @GetMapping("/like/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like(@PathVariable long postId){ // TODO седаль по аналогии как с регистрацией и созданием комментариев
        likesService.create_like(postId);
        return "redirect:/post/{postId}";
    }


    @GetMapping("/unlike/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like(@PathVariable long postId){
       //  String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/like_view_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_sub(@PathVariable long postId){
        likesService.create_like(postId);
        return "redirect:/post-view-sub/{postId}";
    }


    @GetMapping("/unlike_view_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_sub(@PathVariable long postId){
        //  String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/post-view-sub/{postId}";
    }


    @GetMapping("/like_detail/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_detail(@PathVariable long postId){
        likesService.create_like(postId);
        return "redirect:/posts_detail";
    }


    @GetMapping("/unlike_detail/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_detail(@PathVariable long postId){
        //  String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/posts_detail";
    }


    @GetMapping("/like_detail_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_detail_sub(@PathVariable long postId){
        likesService.create_like(postId);
        return "redirect:/";
    }


    @GetMapping("/unlike_detail_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_detail_sub(@PathVariable long postId){
        //  String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/";
    }


    @GetMapping("/like_detail_subuser/{user}/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_detail_subuser(@PathVariable long postId, @PathVariable String user){
        likesService.create_like(postId);
        return "redirect:/posts_detail_subuser/{user}";
    }


    @GetMapping("/unlike_detail_subuser/{user}/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_detail_subuser(@PathVariable long postId, @PathVariable String user){
        //  String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/posts_detail_subuser/{user}";
    }
/*

    @PostMapping("/like")
    @PreAuthorize("hasRole('USER')")
    public String create(LikesDto likes){
        likesService.create_like(likes);
        return "redirect:/post/" + likes.getPostLikeId();
    }

    @PostMapping("/unlike")
    public String create(CommentDto comment){
        commentService.create(comment);
        return "redirect:/post/" + comment.getPostId();
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
    }*/


}
