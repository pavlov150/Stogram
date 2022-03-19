package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
