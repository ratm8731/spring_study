ORM(ORM(Object Relational Mapping)

1. 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)해주는 것
   * 객체 지향 프로그래밍은 클래스를 사용하고, 관계형 데이터베이스는 테이블을 사용한다
   * 객체 모델과 관계형 모델 간에 불일치 존재
   * ORm을 통해 객체 간의 관계를 바탕으로 SQL을 자동으로 생성하여 불일치를 해결
2. 관계형 데이터 베이스 관리 시스템(Relational Database Manager System : RDBMS)의 관계형 데이터 구조와 자바의 객체 지향(Object-Oriented :OO) 모델 사이의 차이를 줄여서 개발자가 객체 모델을 사용해 프로그래밍에 집중하게 하면서 데이터 저장 관련 작업을 쉬게 수행
3. ORM 라이브러리중 가장 성공한 라이브러리 하이버네이트

ORM의 장단점

- 장점
  - 객체 지향적인 코드로 인해 더 직관적이고 비즈니스 로직에 더 집중할 수 있게 도와준다.
    - ORM을 이용하면 SQL Query가 아닌 직관적인 코드(메서드)로 데이터를 조작할 수 있어 개발자가 객체 모델로 프로그래밍하는 데 집중할 수 있도록 도와준다.
    - 선언문, 할당, 종료 같은 부수적인 코드가 없거나 급격히 줄어든다.
    - 각종 객체에 대한 코드를 별도로 작성하기 때문에 코드의 가독성을 올려준다.
    - SQL의 절차적이고 순차적인 접근이 아닌 객체 지향적인 접근으로 인해 생산성이 증가한다.
  - 재사용 및 유지보수의 편리성이 증가한다.
    - ORM은 독립적으로 작성되어있고, 해당 객체들을 재활용 할 수 있다.
    - 때문에 모델에서 가공된 데이터를 컨트롤러에 의해 뷰와 합쳐지는 형태로 디자인 패턴을 견고하게 다지는데 유리하다.
    - 매핑정보가 명확하여, ERD를 보는 것에 대한 의존도를 낮출 수 있다.
  - DBMS에 대한 종속성이 줄어든다.
    - 객체 간의 관계를 바탕으로 SQL을 자동으로 생성하기 때문에 RDBMS의 데이터 구조와 Java의 객체지향 모델 사이의 간격을 좁힐 수 있다.
    - 대부분 ORM 솔루션은 DB에 종속적이지 않다.
    - 종속적이지 않다는것은 구현 방법 뿐만아니라 많은 솔루션에서 자료형 타입까지 유효하다.
    - 프로그래머는 Object에 집중함으로 극단적으로 DBMS를 교체하는 거대한 작업에도 비교적 적은 리스크와 시간이 소요된다.
    - 또한 자바에서 가공할경우 equals, hashCode의 오버라이드 같은 자바의 기능을 이용할 수 있고, 간결하고 빠른 가공이 가능하다.
- 단점
  - 완벽한 ORM 으로만 서비스를 구현하기가 어렵다.
    - 사용하기는 편하지만 설계는 매우 신중하게 해야한다.
    - 프로젝트의 복잡성이 커질경우 난이도 또한 올라갈 수 있다.
    - 잘못 구현된 경우에 속도 저하 및 심각할 경우 일관성이 무너지는 문제점이 생길 수 있다.
    - 일부 자주 사용되는 대형 쿼리는 속도를 위해 SP를 쓰는등 별도의 튜닝이 필요한 경우가 있다.
    - DBMS의 고유 기능을 이용하기 어렵다. (하지만 이건 단점으로만 볼 수 없다 : 특정 DBMS의 고유기능을 이용하면 이식성이 저하된다.)
  - 프로시저가 많은 시스템에선 ORM의 객체 지향적인 장점을 활용하기 어렵다.
    - 이미 프로시저가 많은 시스템에선 다시 객체로 바꿔야하며, 그 과정에서 생산성 저하나 리스크가 많이 발생할 수 있다.

하이버 네이트

- ORM을 구현하는 대표적인 프레임워크가 Hibernate이며, 이를 Java 표준 방식으로 정의한 것이 JPA
- ibernate는 persistence framework
  - Java 환경에서 database로 data를 persist하는데 사용된다.
- Persistence은 영구적인 매체에 데이터를 저장하고, 데이터를 생성 한 애플리케이션이 종료 된 후에도 언제든지 데이터를 검색하는 프로세스
  - db가 connection이 연결이 되어 있지 않아도 db를 가지고 일처리를 할 수 있다

하이버네이트 중점 구조

![image-20200823223847043](C:\Users\kong\AppData\Roaming\Typora\typora-user-images\image-20200823223847043.png)

- SessionFactory
  - SessionFactory는 ConnectionProvider의 세션 및 클라이언트 팩토리
  - 데이터의 2 차 레벨 캐시 (선택 사항)를 보유
  - org.hibernate.SessionFactory 인터페이스는 Session 객체를 얻기위한 팩토리 메소드를 제공

- Session
  - 세션 객체는 응용 프로그램과 데이터베이스에 저장된 데이터 간의 인터페이스를 제공
  - 수명이 짧은 객체이며 JDBC 연결을 래핑합니다. 트랜잭션, 쿼리 및 기준의 팩토리
  - 데이터의 1 차 수준 캐시 (필수)를 보유합니다.
  - org.hibernate.Session 인터페이스는 객체를 삽입, 갱신, 삭제하는 메소드를 제공
  - 또한 Transaction, Query 및 Criteria에 대한 팩토리 메소드를 제공합니다.
- Transaction
  - 트랜잭션 오브젝트는 원자 단위의 작업 단위를 지정합니다.
- ConnectionProvider
  - JDBC connection을 만드는 공장이다.
  - DriverManager or DataSource의 application을 추상화한다.
- TransactionFactory
- JAVA API
  - JDBC (Java Database Connectivity)
  - JTA (Java Transaction API)
  - JNDI (Java Naming Directory Interface)

하이버네이트 SessionFactory 구성

- Session 인터페이스를 기반으로 하며, 해당 인터페이스는 SessionFactory에서 얻을 수 있다
- 스프링은 하이버네이트의 SessionFactory 구성에 사용하는 클래스를 원하는 프로퍼티가 포함 된 빈으로 제공
  - XML 설정
  
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context" 
           xmlns:tx="http://www.springframework.org/schema/tx"
           xmlns:p="http://www.springframework.org/schema/p"
           xmlns:jdbc="http://www.springframework.org/schema/jdbc"
           xmlns:util="http://www.springframework.org/schema/util"
           xsi:schemaLocation="
           http://www.springframework.org/schema/beans 
           http://www.springframework.org/schema/beans/spring-beans.xsd 
           http://www.springframework.org/schema/context 
           http://www.springframework.org/schema/context/spring-context.xsd 
           http://www.springframework.org/schema/tx 
           http://www.springframework.org/schema/tx/spring-tx.xsd
           http://www.springframework.org/schema/jdbc
           http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
           http://www.springframework.org/schema/util
           http://www.springframework.org/schema/util/spring-util.xsd
           ">
    	    
        <jdbc:embedded-database id="dataSource" type="H2">
        	<jdbc:script location="classpath:schema.sql" />
        	<jdbc:script location="classpath:data.sql" />
    	</jdbc:embedded-database>
    	
        <bean id="transactionManager" 
        	class="org.springframework.orm.hibernate5.HibernateTransactionManager" 
        	p:sessionfactory-ref="sessionFactory" />
        	
        <tx:annotation-driven/>
        
        <context:component-scan base-package="com.example.demo" />
        
        <bean id="sessionFactory" 
        	  class="org.springframework.orm.hibernate5.LocalSessionFactoryBean"
        	  p:dataSource-ref="datasource"
        	  p:packageToScan="com.example.demo.entities"
        	  p:hibernatePropertie-ref="hibernateProperties" />
       	
       	<util:properties id="hibernateProperties">
       		 <prop key="hibernate.dialect">org.hibernate.dialect.H2Dialect</prop> <!-- 방언 -->
             <prop key="hibernate.show_sql">true</prop>                   <!-- SQL 보기 -->
             <prop key="hibernate.format_sql">true</prop>                 <!-- SQL 정렬해서 보기 -->
             <prop key="hibernate.use_sql_comments">true</prop>           <!-- SQL 코멘트 보기 -->
             <prop key="hibernate.id.new_generator_mappings">true</prop>  <!-- 새 버전의 ID 생성 옵션 -->
             <prop key="hibernate.hbm2ddl.auto">create</prop>             <!-- DDL 자동 생성 -->
       	</util:properties>
    </beans>
    ```
  
  - JAVA 설정
  
    ```
    package com.example.demo.config;
    
    import java.io.IOException;
    import java.util.Properties;
    
    import javax.sql.DataSource;
    
    import org.hibernate.SessionFactory;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
    import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
    import org.springframework.orm.hibernate5.HibernateTransactionManager;
    import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
    import org.springframework.transaction.PlatformTransactionManager;
    import org.springframework.transaction.annotation.EnableTransactionManagement;
    
    
    @Configuration
    @ComponentScan
    @EnableTransactionManagement
    public class HibernateConfig {
    	private static Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
    	
    	@Bean
    	public DataSource dataSource() {
    		try {
    			EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
    			return dbBuilder.setType(EmbeddedDatabaseType.H2)
    					.addScripts("classpath:sql/schema.sql","classpath:sql/test-data.sql")
    					.build();
    			
    		} catch (Exception e) {
    			logger.error("임베디드 DataSource 빈을 생성할 수 없음!", e);
    			return null;
    			// TODO: handle exception
    		}
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
    		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    		sessionFactoryBean.setDataSource(dataSource());
    		sessionFactoryBean.setPackagesToScan("com.example.demo");
    		sessionFactoryBean.setHibernateProperties(hibernateProperties());
    		sessionFactoryBean.afterPropertiesSet();
    		return sessionFactoryBean.getObject();
    	}
    	
    	@Bean
    	public PlatformTransactionManager transactionManager() throws IOException {
    		return new HibernateTransactionManager(sessionFactory());
    	}	
    }
    ```
  
    - datasource : 임베디드 H2 데이터베이스 선언
    - transactionManager : 
      - 하이버네이트 SessionFactory는 트랜잭션이 필요한 데이터 액세스에 사용할 트랜잭션 매니저가 필요
      - 스프링은 하이버네이트 5 전용 드랜잭션 매니저 제공(org.springframework.orm.hibernate5.HibernateTransactionManager)
      - 트랜잭션 경계 설정(`<tx:annotation-driven/>`,`@EnableTransactionManagement`)
    - component-scan
    - 하이버네이트 SessionFactory :
      - hibernate.dialect, hibernate.show_sql, hibernate.format_sql, 등
      - 프로퍼티 목록을 알고 싶으면 가이드 확인
        - [5.3.18](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html)
        - [5.2.18](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html)

하이버네이트 애너테이션으로 ORM 매핑

- 객체 모델을 설계하고 설계된 객체 모델을 기반으로 데이터베이스 스크립트를 생성

  - SessionFactory 구성에 hibernate.hdm2ddl.auto 하이버네이트 프로퍼티를 전달하면 하이버네이트는 자동으로 스키마 DDL을 데이터베이스로 내보냅니다

- 기존 데이터 모델을 기반으로 해당 모델에 맞는 매핑을 가진 PoJo를 생성하는 방법

  - 데이터 모델을 좀 더 통제할 수 있어 데이터 액세스 성능을 최적화할 때 유용

- 단순 매핑

  ```
  package com.example.demo.entities;
  
  import java.io.Serializable;
  import java.util.Date;
  
  import javax.persistence.Column;
  import javax.persistence.Entity;
  import javax.persistence.GeneratedValue;
  import javax.persistence.GenerationType;
  import javax.persistence.Id;
  import javax.persistence.Table;
  import javax.persistence.Temporal;
  import javax.persistence.TemporalType;
  import javax.persistence.Version;
  
  @Entity
  @Table(name = "user")
  public class User implements Serializable{
  	private Long id;
  	private String name;
  	private String department;
  	private int level;
  	private Date createDate;
  	private int version;
  	
  	@Id
  	@GeneratedValue(strategy = GenerationType.IDENTITY)
  	@Column(name = "ID")
  	public Long getId() {
  		return id;
  	}
  	public void setId(Long id) {
  		this.id = id;
  	}
  	
  	@Column(name = "NAME")
  	public String getName() {
  		return name;
  	}
  	public void setName(String name) {
  		this.name = name;
  	}
  	
  	@Column(name = "DEPARTMENT")
  	public String getDepartment() {
  		return department;
  	}
  	public void setDepartment(String department) {
  		this.department = department;
  	}
  	
  	@Column(name = "LEVEL")
  	public int getLevel() {
  		return level;
  	}
  	public void setLevel(int level) {
  		this.level = level;
  	}
  	@Temporal(TemporalType.DATE)
  	@Column(name = "CREATE_DATE")
  	public Date getCreateDate() {
  		return createDate;
  	}
  	public void setCreateDate(Date createDate) {
  		this.createDate = createDate;
  	}
  	
  	@Version
  	@Column(name = "VERSION")
  	public int getVersion() {
  		return version;
  	}
  	public void setVersion(int version) {
  		this.version = version;
  	}
  	
  	@Override
  	public String toString() {
  		return "User [id=" + id + ", name=" + name + ", department=" + department + ", level=" + level + ", createDate="
  				+ createDate + ", version=" + version + "]";
  	}	
  }
  ```

  - @Id : 
    - 객체의 기본키
    - GeneratedValue id 값 생성 방법 설정
      - AUto : persistence provider가 특정 DB에 맞게 자동 선택
      - IDENTITY : DB의 identity 컬럼을 이용
      - SEQUENCE : DB의 시퀀스 컬럼을 이용
      - TABLE : 유일성이 보장된 데이터베이스 테이블을 이용
  - @Temporal
    - 자바 DATE 타입(`java.util.Date`)을 SQL DATE 타입(`java.sql.Date`)으로 매핑
  - @Version
    - version 애트리뷰트를 제어 수단으로 사용
    - 레코드를 수정할 때마다 엔티티 인스턴스의 version과 데이터베이스 레코드의 version을 비교
    - 버전이 같을 경우 데이터를 수정하고 version 값 증가 다를경우 에러
    - Integer, Timestamp 지원(정수 방식의 버전 관리 추천 - 타임스탬프 동시성 이슈 존재) 

- 일대다 매핑

- 다대다 매핑

데이터 작업