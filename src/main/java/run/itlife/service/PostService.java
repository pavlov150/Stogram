package run.itlife.service;

import run.itlife.dto.PostDto;
import run.itlife.entity.Post;
import java.util.List;

public interface PostService {

    List<Post> listAllPosts();
    long createPost(PostDto postDto);
    List<Post> findByUser(String username);

}
