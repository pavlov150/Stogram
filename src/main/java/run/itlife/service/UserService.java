package run.itlife.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import run.itlife.entity.User;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

}
