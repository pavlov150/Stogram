package run.itlife.service;

import run.itlife.entity.Tag;

import java.util.List;

public interface TagService {

    void createTag(String name);

  //  void createTags(String... names);

    List<Tag> findAll();
}
