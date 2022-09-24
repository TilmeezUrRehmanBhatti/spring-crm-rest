package com.tilmeez.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.tilmeez")
@PropertySource("classpath:persistence-postgresql.properties")
public class AppConfig {

    // set up variable to hold the properties
    @Autowired
    private Environment env;

    // set up a logger for diagnotics
    private Logger logger = Logger.getLogger(getClass().getName());


    // define a bean for our security datasource

    @Bean
    public DataSource myDataSource() {

        // create a connection pool
        ComboPooledDataSource myDataSource
                = new ComboPooledDataSource();

        // get the jdbc driver class
        try {
            myDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        // log the connection props
        logger.info(">>>>> jdbc.url= " + env.getProperty("jdbc.driver"));
        logger.info(">>>>> jdbc.url= " + env.getProperty("jdbc.url"));
        logger.info(">>>>> jdbc.user= " + env.getProperty("jdbc.user"));

        // set connection props

        myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        myDataSource.setUser(env.getProperty("jdbc.user"));
        myDataSource.setPassword(env.getProperty("jdbc.password"));

        // set connection pool props
        myDataSource.setInitialPoolSize(
                getInProperty("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(
                getInProperty("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(
                getInProperty("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(
                getInProperty("connection.pool.maxIdleTime"));

        return myDataSource;
    }

    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        return props;
    }
    // need a helper method

    // read environment property and convert to int

    private int getInProperty(String propName) {

        String propVal = env.getProperty(propName);

        // now convert to int
        int intPropVal = Integer.parseInt(propVal);

        return intPropVal;

    }


    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        // create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }
}





















