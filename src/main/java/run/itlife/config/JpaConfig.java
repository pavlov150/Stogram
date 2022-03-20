package run.itlife.config;

import org.postgresql.Driver;							 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import javax.sql.DataSource;
import java.util.Properties;

//Работаем с Hibernate, используя спецификацию JPA
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("run.itlife.repository")
//@ComponentScan("run.itlife")
public class JpaConfig {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/Stogram";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "NtCn0db4";

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl(URL);
        dataSource.setUsername(LOGIN);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() { //он заменяет Hibernate’овский sessionFactory
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("run.itlife.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() { // это JPA Transaction Manager - для управления транзакциями
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }


    @Bean
    public TransactionOperations transactionOperations() {
        return new TransactionTemplate(transactionManager());
    }

/*    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }*/

    private final Properties hibernateProperties() { // необходим для того, чтобы передать ему базовые свойства
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "none"); // свойство, которое определяет стратегию, будем ли создавать автоматически таблицы
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");

        return hibernateProperties;
    }

}

