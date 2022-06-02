package run.itlife.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Post;
import run.itlife.entity.User;

import java.util.List;
import java.util.Optional;

// Уровень доступа к БД
// JpaRepository - специфически переносит методы для работы с реляционными БД.
//В JpaRepository есть все методы CRUD и много других.
//В репозиториях мы объявляем метод, не реализуя его и он по неймингу (если его правильно называем)
//автоматически понимает какой запрос нужно сделать.
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // Возвращает юзера

    // List<User> findAll(); // Возвращает юзеров

    @Query(value = "select * from users u " +
            "join user_role ur on ur.user_id = u.user_id " +
            "join role r on r.role_id = ur.role_id " +
            "where r.name = 'USER' ", nativeQuery = true)
    List<User> getUsersOnly();


    @Query(value = "select u.* from users u " +
    "where u.username LIKE ? ", nativeQuery = true)
    List<User> searchUsers(String substring);

    @Query(value = "select count(u.username) from users u " +
            "where u.username LIKE ? ", nativeQuery = true)
    int countSearchUsers(String substring);


}