package run.itlife.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

//Класс инициализатора
public class MyWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet("StogramDispatcher", new DispatcherServlet(context));
        //DispatcherServlet - его задача обрабатывать запросы
        //Класс DispatcherServlet является центральным сервлетом, который получает запросы и направляет их соответствующим контроллерам
        //DispatcherServlet создается вместо web.xml
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // --- Encoding for posts -----
        FilterRegistration.Dynamic encodingFilter = container.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        // ------------------------
    }
}