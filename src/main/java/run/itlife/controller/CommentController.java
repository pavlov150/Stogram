package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.dto.CommentDto;
import run.itlife.service.CommentService;

//Контроллер для комментариев (создание)
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    //Принимает он CommentDto - берёт данные с формы post.html, видит, что там есть name=”postId”, есть name="content”
    //и т.д. и он маппит по этим именам на объект CommentDto
    public String create(CommentDto comment){
        commentService.create(comment);
        return "redirect:/post/" + comment.getPostId();
    }
}
