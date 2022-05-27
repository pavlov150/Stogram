package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.dto.CommentDto;
import run.itlife.service.CommentService;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

//Контроллер для комментариев (создание)
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create")
    //Принимает он CommentDto - берёт данные с формы post.html, видит, что там есть name=”postId”, есть name="content”
    //и т.д. и он маппит по этим именам на объект CommentDto
    public String create(CommentDto comment){
        commentService.create(comment);
        return "redirect:/post/" + comment.getPostId();
    }

    @PostMapping("/create_comment_detail")
    public String create_comment_detail(CommentDto comment){
        commentService.create(comment);
        return "redirect:/posts_detail/";
    }

    @PostMapping("/create_comment_detail_sub")
    public String create_comment_detail_sub(CommentDto comment){
        commentService.create(comment);
        return "redirect:/";
    }

    @PostMapping("/create_comment")
    public String create_comment(CommentDto comment){
        commentService.create(comment);
        return "redirect:/comment/" + comment.getPostId();
    }

    @GetMapping("/{id}")
    public String comments(@PathVariable long id, ModelMap modelMap){
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.sortCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
      //  modelMap.put("users", userService.findAll());
      //  modelMap.put("contextPath", context.getContextPath());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));

        return "comments";
    }

}