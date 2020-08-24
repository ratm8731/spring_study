package com.example.demo.hibernate.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
@EnableTransactionManagement
@PropertySource("classpath:db/jdbc.yml")
public class HibernateConfig {
	
	private static Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
	
	@Value("${driverClassName}")
	private String driverClassName;
	
	@Value("${url}")
	private String url;
	
	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	
	@Bean(destroyMethod = "close")
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
	
	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.format_sql", true);
		hibernateProperties.put("hibernate.use_sql_comments", true);
		hibernateProperties.put("hibernate.id.new_generator_mappings", true);
		hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
		return hibernateProperties;
	}
	
	@Bean
	public SessionFactory sessionFactory() throws IOException {
		return new LocalSessionFactoryBuilder(getDataSource())
				.scanPackages("com.example.demo")
				.addProperties(hibernateProperties())
				.buildSessionFactory();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws IOException {
		return new HibernateTransactionManager(sessionFactory());
	}
}
