package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import run.itlife.dto.CommentDto;
import run.itlife.entity.Comment;
import run.itlife.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.List;
import static run.itlife.utils.SecurityUtils.*;
import static run.itlife.utils.SecurityUtils.ACCESS_DENIED;

// Уровень обслуживания
// Класс, реализующий интерфейс, который отвечает за логику создания комментариев
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
        comment.setCommentText(commentDto.getCommentText());
        comment.setUser(userService.findByUsername(getCurrentUserDetails().getUsername()));
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public void delete(long id) {
        String username = commentRepository.findById(id)
                .orElseThrow()
                .getUser().getUsername();
        if (!hasAuthority(username) && !hasRole("ADMIN")) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> sortCommentsByDate(long id) {
            List<Comment> comments = commentRepository.sortCommentsByDate(id);
            return comments;
    }

}