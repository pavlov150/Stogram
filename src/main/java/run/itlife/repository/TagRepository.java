package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Tag;

import java.util.List;

// Уровень доступа к БД
// JpaRepository - специфически переносит методы для работы с реляционными БД.
//В JpaRepository есть все методы CRUD и много других.
//В репозиториях мы объявляем метод, не реализуя его и он по неймингу (если его правильно называем)
//автоматически понимает какой запрос нужно сделать.
public interface TagRepository extends JpaRepository<Tag, Long> {

    /*@Query(value = "select p.*, t.*, pt.* from tag t " +
            "join post_tag pt on pt.tag_id = t.tag_id " +
            "join post p on p.post_id = pt.post_id " +
            "join users u on p.user_id = u.user_id " +
            "where u.username = ? and p.post_id = ?", nativeQuery = true)
    List<Tag> findByTagName(String username, long postId);*/
}