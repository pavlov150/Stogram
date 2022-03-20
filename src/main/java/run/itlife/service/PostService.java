package run.itlife.service;

import run.itlife.dto.PostDto;
import run.itlife.entity.Post;
import java.util.List;

//Интерфейс, отвечающий за логику создания постов, валидацию, изменение и т.д.
public interface PostService {

    List<Post> listAllPosts();

    long createPost(PostDto postDto);

    List<Post> findByUser(String username);

    void checkAuthority(long postId);

    PostDto getAsDto(long postId);

    void update(PostDto postDto);

    Post findById(long id);

    void delete(long id);

}