package run.itlife.service;

import run.itlife.dto.CommentDto;
import run.itlife.entity.Comment;

import java.util.List;

//Интерфейс, отвечающий за логику создания комментариев
public interface CommentService {

    void create(CommentDto comment);

    List<Comment> sortCommentsByDate(long id);

}