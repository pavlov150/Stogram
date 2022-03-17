package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
