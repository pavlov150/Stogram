package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.itlife.dto.PostDto;
import run.itlife.entity.Post;
import run.itlife.entity.Tag;
import run.itlife.repository.PostRepository;
import run.itlife.repository.TagRepository;
import run.itlife.repository.UserRepository;
import run.itlife.utils.SecurityUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
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

    @Override
    public long createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTags(parseTags(postDto.getTags()));
        post.setCreatedAt(LocalDateTime.now());

        String username = SecurityUtils.getCurrentUserDetails().getUsername();
        post.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        postRepository.save(post);
        return post.getPostId();
    }

    private List<Tag> parseTags(String tags) {
        if (tags == null)
            return Collections.emptyList();
        return Arrays.stream(tags.split(" "))
                .map(tagName -> tagRepository.save(new Tag(tagName)))
                .collect(Collectors.toList());
    }
}
