package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Likes;

import javax.transaction.Transactional;
import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    // сколько постов и какие пролайкал текущий пользователь
    @Query(value = "select * from likes l " +
            "join users u on u.user_id = l.user_id " +
            "join post p on p.post_id = l.post_id " +
            "where u.username = ? ", nativeQuery = true)
    List<Likes> searchLikesByUsername(String currentUsername);

    // сколько лайков у одного поста
    @Query(value = "select count(p.post_id) from likes l " +
            "join users u on u.user_id = l.user_id " +
            "join post p on p.post_id = l.post_id " +
            "where p.post_id = ? ", nativeQuery = true)
    int countLikesByPostId(long postId);

    @Query(value = "select count(p.post_id) from likes l " +
            "join users u on u.user_id = l.user_id " +
            "join post p on p.post_id = l.post_id " +
            "join users u1 on u1.user_id = l.user_id " +
            "where u1.username = ? ", nativeQuery = true)
    int countLikesByUsername(String username);

    //кто пролайкал текущий пост
    @Query(value = "select * from likes l " +
            "join users u on u.user_id = l.user_id " +
            "join post p on p.post_id = l.post_id " +
            "where p.post_id = ? ", nativeQuery = true)
    List<Likes> searchUsernameByPostId(long postId);

    //если текущий пользователь пролайкал текущий пост
    @Query(value = "select count(p.post_id) from likes l " +
            "join users u on u.user_id = l.user_id " +
            "join post p on p.post_id = l.post_id " +
            "where p.post_id = ? and u.username = ? ", nativeQuery = true)
    int isLikePostForCurrentUser(long postId, String currentUsername);

    @Modifying
    @Transactional
    @Query(value = "delete from likes l " +
            "where user_id = ? and post_id = ? ; ", nativeQuery = true)
    void delete_like(long userId, long postId);

}
