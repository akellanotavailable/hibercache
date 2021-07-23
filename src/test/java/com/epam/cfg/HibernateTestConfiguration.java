package com.epam.cfg;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.epam")
@EnableTransactionManagement
@PropertySource(value = "application.properties")
public class HibernateTestConfiguration {

    @Bean
    public DataSource mySQLDataSource(@Value("${database.url}") String url,
                                      @Value("${database.user}") String user,
                                      @Value("${database.password}") String password) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws SQLException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.epam");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
        hibernateProperties.setProperty("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");

        return hibernateProperties;
    }
}
