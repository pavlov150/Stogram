package run.itlife.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import run.itlife.dto.PostDto;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import java.util.List;

//Интерфейс, отвечающий за логику создания пользователей, поиск пользователей
public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    List<User> findAll();

    void create(User user);

    void update(UserDto userDto);

    public void checkAuthority(long userId);

}