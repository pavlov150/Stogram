package run.itlife.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Security;
import java.util.Objects;
import java.util.stream.Collector;		  
import java.util.stream.Collectors;

//Настройка безопасности
public class SecurityUtils {

    public static final String ACCESS_DENIED = "Access Denied";

    public static UserDetails getCurrentUserDetails() {
        //SecurityContext - здесь хранится информация о пользователях в текущий момент
        //getPrincipal - информация о пользователе будет строкой, если мы не залогинились с общим названием
        //для обычных юзеров, типа анонимный юзер. А если залогинились, то это будет объект класса, который имитирует UserDetails
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!(principal instanceof  UserDetails))
            throw new AccessDeniedException(ACCESS_DENIED);

        return (UserDetails)principal;
    }

    //Закрытие доступа к странице для всех кроме USERS
    public static boolean hasRole(String role){
        return getCurrentUserDetails().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .contains("ROLE_" + role);
    }

    public static boolean hasAuthority(String username){
        return Objects.equals(username, getCurrentUserDetails().getUsername());
    }

    public static void checkAuthority(String username) {
        if (!hasAuthority(username))
            throw new AccessDeniedException(ACCESS_DENIED);
     //   if(!Objects.equals(username, getCurrentUserDetails().getUsername()))
     //       throw new AccessDeniedException(ACCESS_DENIED);
    }
}
