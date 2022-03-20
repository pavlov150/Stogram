package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.Comment;

//JpaRepository - специфически переносит методы для работы с реляционными БД.
//В JpaRepository есть все методы CRUD и много других.
//В репозиториях мы объявляем метод, не реализуя его и он по неймингу (если его правильно называем)
//автоматически понимает какой запрос нужно сделать.
public interface CommentRepository extends JpaRepository<Comment, Long> {}