package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.itlife.dto.CommentDto;
import run.itlife.entity.Comment;
import run.itlife.repository.CommentRepository;

import java.time.LocalDateTime;

import static run.itlife.utils.SecurityUtils.getCurrentUserDetails;

//Класс, реализующий интерфейс, который отвечает за логику создания комментариев
@Service
public class CommentServiceImpl implements CommentService {

    // сервисы в свою очередь включают репозиторий
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostService postService,
                              UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    //Имя пользователя достаётся во время создания комментария. Реализация в CommentServiceImpl в методе create.
    public void create(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setPost(postService.findById(commentDto.getPostId()));
        comment.setContent(commentDto.getContent());
        comment.setUser(userService.findByUsername(getCurrentUserDetails().getUsername()));
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
