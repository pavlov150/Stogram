package run.itlife.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import run.itlife.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);
    List<User> findAll();

}
