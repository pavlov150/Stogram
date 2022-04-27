package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.expression.Lists;
import run.itlife.dto.PostDto;
import run.itlife.entity.Post;
import run.itlife.entity.Tag;
import run.itlife.repository.PostRepository;
import run.itlife.repository.TagRepository;
import run.itlife.repository.UserRepository;
import run.itlife.utils.SecurityUtils;
import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static run.itlife.utils.SecurityUtils.*;

// Уровень обслуживания
// Класс, реализующий интерфейс, который отвечает за логику создания постов, валидацию, изменение и т.д.
@Service
@Transactional
public class PostServiceImpl implements PostService {

    // сервисы в свою очередь включают репозиторий
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
        List<Post> posts =  postRepository.findAll(Sort.by("createdAt").descending());
        for (Post p : posts) {
            p.getTags().size();
            p.getComments().size();
        }
        return posts;
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
        post.setPhoto(postDto.getPhoto());
        post.setContent(postDto.getContent());
        post.setTags(parseTags(postDto.getTags()));
        post.setCreatedAt(LocalDateTime.now());
        String username = SecurityUtils.getCurrentUserDetails().getUsername();
        post.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        postRepository.save(post);
        return post.getPostId();
    }

    @Override
    public void checkAuthority(long postId) {
        SecurityUtils.checkAuthority(postRepository.findById(postId)
                .orElseThrow()
                .getUser().getUsername());
    }

    @Override
    public PostDto getAsDto(long postId) {
        return toDto(postRepository.findById(postId).orElseThrow());
    }

    @Override
    public void update(PostDto postDto) {
        checkAuthority(postDto.getPostId());
        Post post = postRepository.findById(postDto.getPostId()).orElseThrow();
        if (!StringUtils.isEmpty(postDto.getPhoto()))
            post.setPhoto(postDto.getPhoto());
        if (!StringUtils.isEmpty(postDto.getContent()))
            post.setContent(postDto.getContent());
        if (!StringUtils.isEmpty(postDto.getTags()))
            post.setTags(parseTags(postDto.getTags()));
        postRepository.save(post);
    }

    @Override
    public Post findById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.getTags().size();
        post.getComments().size();
        return post;
    }

    @Override
    public void delete(long id) {
        String username = postRepository.findById(id)
                .orElseThrow()
                .getUser().getUsername();
        if (!hasAuthority(username) && !hasRole("ADMIN")) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> findByUserName(String username) {
        List<Post> posts = postRepository.findByUserName(username);
        for (Post p : posts) {
            p.getTags().size();
            p.getComments().size();
        }
        return posts;
    }



    private PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setPostId(post.getPostId());
        dto.setPhoto(post.getPhoto());
        dto.setContent(post.getContent());
        dto.setTags(post.getTags()// TODO пример для получения тегов конкретного поста
                .stream()
                .map(Tag::getName)
                .collect(Collectors.joining(" ")));
        return dto;
    }

    private List<Tag> parseTags(String tags) {
        if (tags == null) return Collections.emptyList();
        return Arrays
                .stream(tags.split(" "))
                .map(tagName -> tagRepository.save(new Tag(tagName)))
                .collect(Collectors.toList());
    }

}