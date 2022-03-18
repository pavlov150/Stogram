package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
