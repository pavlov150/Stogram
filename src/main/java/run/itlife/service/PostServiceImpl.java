package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.itlife.entity.Post;
import run.itlife.repository.PostRepository;
import run.itlife.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> listAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findByUser(String username) {
        List<Post> posts = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
                .getPosts();
        posts.size();
        return posts;
    }
}
