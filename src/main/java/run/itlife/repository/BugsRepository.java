package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Bugs;
import run.itlife.entity.Comment;
import run.itlife.entity.Post;

import java.util.List;

public interface BugsRepository extends JpaRepository<Bugs, Long> {
}
