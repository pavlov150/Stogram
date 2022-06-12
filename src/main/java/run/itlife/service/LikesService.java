package run.itlife.service;

public interface LikesService {

    int countLikesByPostId(long postId);
    int countLikesByUsername(String username);
    int isLikePostForCurrentUser(long postId, String currentUsername);
    void create_like(Long postId);
    void delete_like(long userId, long postId);

}
