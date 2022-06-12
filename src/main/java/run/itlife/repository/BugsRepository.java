package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.Bugs;

public interface BugsRepository extends JpaRepository<Bugs, Long> {
}
