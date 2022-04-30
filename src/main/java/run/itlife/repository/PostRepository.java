package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Post;
import run.itlife.entity.Tag;

import java.util.List;

// Уровень доступа к БД
// JpaRepository - специфически переносит методы для работы с реляционными БД.
//В JpaRepository есть все методы CRUD и много других.
//В репозиториях мы объявляем метод, не реализуя его и он по неймингу (если его правильно называем)
//автоматически понимает какой запрос нужно сделать.
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p.*, u.username, string_agg(t.name, '') name_t from post p " +
            "join users u on p.user_id = u.user_id " +
            "join post_tag pt on p.post_id = pt.post_id " +
            "join tag t on pt.tag_id = t.tag_id " +
            "where u.username = ? " +
            "GROUP BY p.post_id, u.username;" , nativeQuery = true)
    List<Post> findByUserName(String username);

    @Query(value = "select count(p.post_id) from post p " +
    "join users u on p.user_id = u.user_id " +
    "where u.username = ? ", nativeQuery = true)
    int countPosts(String username);

    @Query(value = "select count(c.post_id), p.post_id from post p " +
            "join comment c on c.post_id = p.post_id where c.post_id = ? " +
            "GROUP BY p.post_id ", nativeQuery = true)
    Long countComments(Long id);




}