

JPA (Java Persistence API) 란

 	1. 자바 ORM 기술에 대한 표준 명세로,  JAVA에서 제공하는 API
 	2. orm을 사용하기 위해 만든 인터페이스
 	3. JPA 동작 과정
     - JPA는 애플리케이션과 JDBC 사이에서 동작
     - **개발자가 JPA를 사용하면, JPA 내부에서 JDBC API를 사용하여 SQL을 호출하여 DB와 통신**

![jpa](https://github.com/ratm8731/spring_study/blob/master/img/jpa.png)

![JPA1](https://github.com/ratm8731/spring_study/blob/master/img/JPA1.png)

 	4. JPA 특징
     - 데이터를 객체지향적으로 관리할 수 있기 때문에 개발자는 비즈니스 로직에 집중
     - 자바 객체와 DB 테이블 사이의 매핑 설정을 통해 SQL을 생성
     - 객체를 통해 쿼리를 작성할 수 있는 JPQL(Java Persistence Query Language)를 지원
     - JPA는 성능 향상을 위해 지연 로딩이나 즉시 로딩과 같은 몇가지 기법을 제공하는데 이것을 잘 활용하면 SQL을 직접 사용하는 것과 유사한 성능
	5. EntityManagerFactory와 EntityManager
    - 데이터베이스와의 상호 작용을 위해 EntityManager를 생성하기 위해 사용되는데 EntityManagerFactory는 애플리케이션 전체에서 딱 한번만 생성하고 공유해서 사용 해야한다
    - 도메인 객체를 테이블로 변환, 엔티티와 관련된 모든 일을 한다.(저장, 조회, 수정, 삭제), DB에서 엔티티를 가져오거나 생성, 삭제, 수정하는 일은 모두 EntityManager를 통해 이루어 진다
    - 엔티티 저장 요청시 엔티티 객체를 생성하여 DB에 저장하며 SQL과 같은 CRUD 오퍼레이션을 제공
    - EntityManager의 인스턴스는 영속성 컨텍스트(Persistence Context)를 나타내며 EntityManagerFactory를 통해서 얻는다
	6. EntityManagerFactory 구성
    	1. LocalEntityManagerFactoryBean
        - DataSource를 주입 할 수 없어 분산 트랙잭션 사용 불가
    	2. JEE 호환 컨테이너가 제공하는 EntityManagerF
        - JNDI 룩업 사용 가능
    	3. LocalContainerEntityManagerFactoryBean
        - DataSource 주입과 로컬 및 분산 트랜잭션 가능



JPA로 데이터베이스 조작하기

- 조회

  - createNamedQuery

    - 기본 조회

    ```
    public List<AgentEntity> findAll() {
        return entityManager.createNamedQuery(AgentEntity.FIND_ALL, AgentEntity.class).getResultList();
    }
    ```

    - 명시되지 않은 타입의 결과 쿼리

    ```
    List result = entityManager.createQuery(
            "select agent.name, agent.software.size, software.name from AgentEntity agent " +
            "left join agent.software software " +
            "where software.insertDate = " +
            "(select max(software2.insertDate) from SoftwareEntity software2 where software2.agent.id = agent.id)")
            .getResultList();
    
    for (Iterator i = result.iterator(); i.hasNext(); ) {
                Object[] values = (Object[]) i.next();
                String agentName = (String) values[0];
                int size = (int) values[1];
                String softwareName = (String) values[2];
                logger.info("agent name : " + agentName);
                logger.info("software size : " + size);
                logger.info("last install software name : " + softwareName);
    }
            
    ```

    - 생성자 표현식을 사용한 커스텀 결과 타입 쿼리

  - ```
    List<AgentSummary> agentSummaryList = entityManager.createQuery(
            "select new com.example.demo.jpa.entity.AgentSummary("+
            "agent.name, software.name) from AgentEntity agent " +
            "left join agent.software software " +
            "where software.insertDate = " +
            "(select max(software2.insertDate) from SoftwareEntity software2 where software2.agent.id = agent.id)", AgentSummary.class
    ).getResultList();
    ```

- 저장, 수정

  - ```
    @Override
    public void save(AgentEntity agent) {
        if (agent.getId() == null) {
            entityManager.persist(agent);
            logger.info("에이전트 등록");
        } else {
            entityManager.merge(agent);
            logger.info("에이전트 수정");
        }
    
    }
    ```

- 삭제

  - ```
    public void delete(AgentEntity agent) {
        AgentEntity mergedContact = entityManager.merge(agent);
        entityManager.remove(mergedContact);
        logger.info("에이전트 ( " +agent.getId()+ " ) 를 삭제했습니다.");
    }
    ```

네이티브 쿼리 사용하기

- 클래스 타입으로 반환

  ```
  public List<AgentEntity> fineAllByNativeQuery() {
      return entityManager.createNativeQuery(ALL_AGENT_NATIVE_QUERY, AgentEntity.class).getResultList();
  }
  ```

- SQL ResultSet 매핑

  ```
  @SqlResultSetMapping(
  		name = "agentResult",
  		entities = @EntityResult(entityClass = AgentEntity.class)
  )
  
  public List<AgentEntity> fineAllByNativeQuery() {
      return entityManager.createNativeQuery(ALL_AGENT_NATIVE_QUERY, "agentResult").getResultList();
  }
  ```

Criteria 쿼리 사용하기

```
public AgentEntity findByCriteriaQuery(String agentname) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<AgentEntity> criteriaQuery = cb.createQuery(AgentEntity.class);
    Root<AgentEntity> agentEntityRoot = criteriaQuery.from(AgentEntity.class);
    agentEntityRoot.fetch(AgentEntity_.software, JoinType.LEFT);
    agentEntityRoot.fetch(AgentEntity_.adminSendMessage, JoinType.LEFT);

    criteriaQuery.select(agentEntityRoot).distinct(true);

    Predicate criteria = cb.conjunction();

    if (agentname != null) {
        Predicate p = cb.equal(agentEntityRoot.get(AgentEntity_.name),agentname);
        criteria = cb.and(criteria, p);
    }

    criteriaQuery.where(criteria);

    return entityManager.createQuery(criteriaQuery).getSingleResult();
}
```

- entityManager.getCriteriaBuilder() 를 호출 해 인스턴스를 가져옴
- 결과 타입을 AgentEntity로 지정
- criteriaQuery.from을 호출. 호출 결과 지정된 엔티티의 쿼리 루트 객체 반환(경로 표현식의 기반)
- agentEntityRoot.fetch 연관 관계 연결
- criteriaQuery.select(agentEntityRoot).distinct(true) 을 통해 반환 받을 타입과 중복제거
- cb.conjunction() Predicate 인스턴스를 가져옴(결합 사항을 하나 이상 결합할때 사용)
- cb.and(criteria, p) 검색 조건 추가
- criteriaQuery.where(criteria) 최종 검색 조건을 적용
- entityManager.createQuery(criteriaQuery).getSingleResult() 최종 CriteriaQuery를  EntityManager에게 넘기며 EntityManager는 인자로 받은 CriteriaQuery를 기반으로 쿼리를 생성하고 실행한 뒤 결과를 반환

스프링 데이터 JpaRepository 사용하기

- JPA EntityManager를 래핑해 더 단순화된 JPA 기반의 데이터 액세스 인터페이스를 제공
- CrudRepository
  - 기본적인 저장, 삭제, 조회 기능 제공
- JpaRepository
  - 배치, 페이징, 정렬 기능을 제공
  - CrudRepository를 상속
- 스프링 데이터 JPA로 커스텀 쿼리 사용하기
  - @Query 어노테이션

하이버네이트 엔버스

- 엔티티 버전 관리 자동화에 특화된 하이버네이트 모듈
- 레코드 수정 관련 컬럼 관리
- 유효성 검증 감사

