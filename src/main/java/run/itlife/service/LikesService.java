package run.itlife.service;

import org.springframework.data.jpa.repository.Query;
import run.itlife.dto.CommentDto;
import run.itlife.dto.LikesDto;
import run.itlife.entity.Likes;

import java.util.List;

public interface LikesService {

   // List<Likes> searchLikesByUsername(String username);

    int countLikesByPostId(long postId);

    int countLikesByUsername(String username);

 //   List<Likes> searchUsernameByPostId(long postId);

    int isLikePostForCurrentUser(long postId, String currentUsername);

    //void create_like(LikesDto likesdto);

    void create_like(Long postId);

    void delete_like(long userId, long postId);

}
