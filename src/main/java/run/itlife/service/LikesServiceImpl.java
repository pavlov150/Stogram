package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Likes;
import run.itlife.repository.LikesRepository;

import static run.itlife.utils.SecurityUtils.getCurrentUserDetails;

@Service
@Transactional
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public LikesServiceImpl(LikesRepository likesRepository, PostService postService, UserService userService) {
        this.likesRepository = likesRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public int countLikesByPostId(long postId) {
        return likesRepository.countLikesByPostId(postId);
    }

    @Override
    public int countLikesByUsername(String username) {
        return likesRepository.countLikesByUsername(username);
    }


    @Override
    public int isLikePostForCurrentUser(long postId, String currentUsername) {
        return likesRepository.isLikePostForCurrentUser(postId, currentUsername);
    }

    @Override
    public void create_like(Long postId) {
        Likes likes = new Likes();
        likes.setUserLikeId(userService.findByUsername(getCurrentUserDetails().getUsername()));
        likes.setPostLikeId(postService.findById(postId));
        likesRepository.save(likes);
    }

    @Override
    public void delete_like(long userId, long postId) {
        likesRepository.delete_like(userId, postId);
    }

}
