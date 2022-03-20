package run.itlife.service;

import run.itlife.dto.CommentDto;

//Интерфейс, отвечающий за логику создания комментариев
public interface CommentService {
    void create(CommentDto comment);

}