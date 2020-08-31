package com.example.demo.jpa.config;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.example.demo.jpa")
@EnableTransactionManagement
@PropertySource("classpath:db/jdbc.properties")
@EnableJpaRepositories(basePackages = {"com.example.demo.jpa.repository"})
@EnableJpaAuditing(auditorAwareRef = "auditorAwareBean")
public class JPAConfig {
	
	private static Logger logger = LoggerFactory.getLogger(JPAConfig.class);
	
	@Value("${driverClassName}")
	private String driverClassName;
	
	@Value("${url}")
	private String url;
	
	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;


	
	@Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
    @Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		return new HibernateJpaVendorAdapter();
	}
	@Bean
	public Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.format_sql", true);
		hibernateProperties.put("hibernate.use_sql_comments", true);
		hibernateProperties.put("hibernate.id.new_generator_mappings", true);
		hibernateProperties.put("hibernate.hbm2ddl.auto", "create");

		// hibernate Envers 관련 프로퍼티
		hibernateProperties.put("org.hibernate.envers.audit_table_suffix","_History");
		hibernateProperties.put("org.hibernate.envers.revision_field_name","AUDIT_REVISION");
		hibernateProperties.put("org.hibernate.envers.revision_type_field_name","ACTION_TYPE");
		hibernateProperties.put("org.hibernate.envers.audit_strategy","org.hibernate.envers.strategy.ValidityAuditStrategy");


		return hibernateProperties;
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() throws IOException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("com.example.demo.jpa.entity");
		factoryBean.setDataSource(getDataSource());
		factoryBean.setJpaProperties(hibernateProperties());
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.afterPropertiesSet();
		return factoryBean.getNativeEntityManagerFactory();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws IOException {
		return new JpaTransactionManager(entityManagerFactory());
	}
}
