package run.itlife.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Добавили класс AppInitializer, чтобы WebConfig поднимался через него
//До этого работали без AppInitializer
//и Spring’овый DispatcherServlets инициализировался через MyWebAppInitializer и этого было достаточно
//Чтобы подключить WebSecurity нужен и AppInitializer для инициализации основного приложения,
//который ссылается на объект Config и SecurityWebAppInitializer для инициализации WebConfig
//AppInitializer, MyWebAppInitializer и SecurityWebAppInitializer нужны просто для того,
//чтобы произошла инициализация. Просто, чтобы наши конфигурации (WebConfig и WebSecurityConfig) подхватились.
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses () {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses () {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings () {
        return new String[]{"/"};
    }
}