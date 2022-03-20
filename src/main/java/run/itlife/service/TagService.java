package run.itlife.service;

import run.itlife.entity.Tag;
import java.util.List;

//Интерфейс, отвечающий за логику создания тегов
public interface TagService {

    void createTag(String name);

    List<Tag> findAll();

}