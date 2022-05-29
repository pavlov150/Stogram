package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Comment;
import run.itlife.entity.Post;

import java.util.List;

// Уровень доступа к БД
// JpaRepository - специфически переносит методы для работы с реляционными БД.
//В JpaRepository есть все методы CRUD и много других.
//В репозиториях мы объявляем метод, не реализуя его и он по неймингу (если его правильно называем)
//автоматически понимает какой запрос нужно сделать.
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select u.username, u.photo, c.* from comment c " +
            "join post p on p.post_id = c.post_id " +
            "join users u on c.user_id = u.user_id " +
            "where p.post_id = ? " +
            "order by c.created_at ", nativeQuery = true)
    List<Comment> sortCommentsByDate(long id);

   /* @Query(value = "select * from comment " +
            "order by created_at desc " +
            "LIMIT 2 ", nativeQuery = true)
    List<Comment> getLastComments();*/ /* TODO для post-detail вывести последние 2 сообщения и добавить счетчик */

}