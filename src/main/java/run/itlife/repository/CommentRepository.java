package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
