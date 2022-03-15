package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}

