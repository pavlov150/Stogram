package run.itlife.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//Чтобы поднять WebSecurityConfig используем специальный SecurityWebAppInitializer
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebAppInitializer() {
        super(WebSecurityConfig.class);
    }

}