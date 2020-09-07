트랜잭션 관리

- 트랜잭션(transaction) 
  - 하나의 논리적 기능을 수행하기 위한 작업의 단위
  - 트랜잭션의 특성(ACID)
    - 원자성(Atomicity) : 트랜잭션은 자기의 연산을 전부 또는 전무 실행만이 있지 일부 실행으로 트랜잭션의 기능을 갖는 것은 아니다.
    - 일관성(Consistency) : 트랜잭션이 그 실행을 성공적으로 완료하면 언제나 일관성있는 데이타베이스 상태로 변환한다. 즉, 트랜잭션 실행의 결과로 데이터베이스 상태가 모순되지 않는다.
    - 독립성(Isolation) : 트랜잭션이 실행 중에 있는 연산의 중간 결과는 다른 트랜잭션이 접근할 수 없다.
    - 영속성(Durability) : 트랜잭션이 일단 그 실행을 성공적으로 완료하면 그 결과는 영속적이다. 따라서 시스템은 어떤 경우에도 완료된 결과의 영속성을 보장해야 한다.
  - 트랜잭션의 상태
    - 활동(active) : 트랜잭션이 Begin_Trans에서부터 실행을 시작하였거나 실행 중인 상태
    - 부분 완료(partially committed) : 트랜잭션이 마지막 명령문을 실행한 직후의 상태
    - 실패(failed) : 정상적 실행을 더 이상 계속할 수 없어서 중단한 상태
    - 철회(aborted) : 트랜잭션이 실행에 실패하여 Rollback 연산을 수행한 상태
    - 완료(committed) : 트랜잭션이 실행을 성공적으로 완료하여 Commit 연산을 수행한 상태

- PlatformTransactionManager

  - TransactionDefinition과 TransactionStatus를 사용해 트랜잭션을 생성하고 관리

  - 다양한 구현체 제공

    - CciLocalTransactionManager - 클라이언트 인터페이스
    - DataSourceTransactionManager - JDBC 
    - JpaTransactionManager - JPA
    - HibernateTransactionManager - 하이버네이트
    - 그외 다수 제공

    | 속성            | 필수 여부 | 기본값   | 설명                                                         |
    | :-------------- | --------- | -------- | ------------------------------------------------------------ |
    | name            | Y         |          | 트랜잭션 속성이 적용될 메서드 이름. 다수의 메서드에 같은 설정을 적용하기 위해 와일드카드 (*) 를 사용할 수 있다. 예로, get*, handle*, on*Event 등등 |
    | propagation     | N         | REQUIRED | 트랜잭션 전파 동작                                           |
    | isolation       | N         | DEFAULT  | 트랜잭션 격리 수준                                           |
    | timeout         | N         | -1       | 트랜잭션 타임아웃 값                                         |
    | read-only       | N         | false    | 읽기 전용 트랜잭션인가?                                      |
    | rollback-for    | N         |          | 롤백을 적용할 Exception(s). 콤마로 구분한다. 예로, com.foo.MyBusinessException,ServletException |
    | no-rollback-for | N         |          | 롤백을 적용하지 않을 Exception(s). 콤마로 구분한다. 예로, com.foo.MyBusinessException,ServletException |

  - TransactionDefinition

    - 격리(Isolation): 이 트랜잭션이 다른 트랜잭션으로부터 격리되는 정도. 예로, 이 트랜잭션이 다른 트랜잭션의 커밋되지 않은 쓰기 작업을 볼 수 있는가?

    - 전파(Propagation): 보통 한 트랜잭션 범위 안에서 실행된 코드는 그 트랜잭션 안에서 동작한다. 그러나 이미 트랜잭션이 존재할 때 트랜잭션 관련 메서드가 실행될 경우 트랜잭션을 어떻게 묶을 것인지 선택할 수 있다. 예로, 기존 트랜잭션 안에서 실행할 것인가, 혹은 생성된 새로운 트랜잭션으로 실행하여 기존 트랜잭션과 분리할 것인가. 스프링은 EJB CMT 와 유사한 모든 트랜잭션 전파 옵션을 제공한다. 스프링의 트랜잭션 전파의 의미에 대해서는 트랜잭션 전파 에서 읽어볼 수 있다.

      - REQUIRED (기본값) : 

        1. 부모 트랜잭션이 존재한다면 부모 트랜잭션으로 합류합니다. 

        2. 부모 트랜잭션이 없다면 새로운 트랜잭션을 생성합니다.

        3. 중간에 롤백이 발생한다면 모두 하나의 트랜잭션이기 때문에 진행사항이 모두 롤백됩니다.

        ![required.png](https://github.com/ratm8731/spring_study/blob/master/img/required.png?raw=true)

      - REQUIRES_NEW : 

        1. 무조건 새로운 트랜잭션을 생성합니다. 

        2. 각각의 트랜잭션이 롤백되더라도 서로 영향을 주지 않습니다.

        ![requires_view.png](https://github.com/ratm8731/spring_study/blob/master/img/requires_view.png?raw=true)

      - MANDATORY : 

        1. 부모 트랜잭션에 합류합니다. 

        2. 부모 트랜잭션이 없다면 예외를 발생시킵니다.

        ![mandatory.png](https://github.com/ratm8731/spring_study/blob/master/img/mandatory.png?raw=true)

      - NASTED :

        1. 부모 트랜잭션이 존재한다면 중첩 트랜잭션을 생성합니다. 

        2. 중첩된 트랜잭션 내부에서 롤백 발생시 해당 중첩 트랜잭션의 시작 지점 까지만 롤백됩니다. 

        3. 중첩 트랜잭션은 부모 트랜잭션이 커밋될 때 같이 커밋됩니다.

        4. 부모 트랜잭션이 존재하지 않는다면 새로운 트랜잭션을 생성합니다.

        ![nastedimg.png](https://github.com/ratm8731/spring_study/blob/master/img/nastedimg.png?raw=true)

      - NEVER : 

        1. 트랜잭션을 생성하지 않습니다. 

        2. 부모 트랜잭션이 존재한다면 예외를 발생시킵니다.

        ![naver.png](https://github.com/ratm8731/spring_study/blob/master/img/naver.png?raw=true)

      - SUPPORTS : 

        1. 부모 트랜잭션이 있다면 합류합니다. 
        2. 진행중인 부모 트랜잭션이 없다면 트랜잭션을 생성하지 않습니다.

      - NOT_SUPPORTED : 

        1. 부모 트랜잭션이 있다면 보류시킵니다. 
        2. 진행중인 부모 트랜잭션이 없다면 트랜잭션을 생성하지 않습니다.

    - 타임아웃: 트랜잭션 인프라에 의하여, 이 트랜잭션 수행이 자동 타임아웃-롤백 될 때까지 얼마만큼의 시간이 주어지는가.

    - 읽기 전용 상태: 읽기 전용 트랜잭션은 트랜잭션이 데이터를 읽기는 하지만 수정할 수는 없도록 할 때 사용된다. 읽기 전용 트랜잭션은 하이너베이트를 사용할 때와 같은 몇몇 경우에 유용할 최적화가 될 수 있다.

  - TransactionStatus

    - 트랜잭션 코드가 트랜잭션 실행과 쿼리 트랜잭션 상태를 제어

- 선언적 트랜잭션 관리

  - 대부분의 스프링 프레임워크 사용자는 선언적 트랜잭션 관리를 사용한다. 이 방법은 어플리케이션 코드로의 영향도가 가장 적고, 때문에 비 침습성 경량 컨테이너의 이상과 가장 일치한다.
  - 트랜잭션 동작을 독립적인 메서드 단위로 지정할 수 있다는 점에서 EJB CMT 와 유사
  - 간단히 설정을 바꾸는 것으로 JTA 트랜잭션, JDBC 를 이용한 로컬 트랜잭션, JPA, 하이버네이트 또는 JDO 등의 다양한 트랜잭션 환경에서 작동
  -  EJB 에 존재하지 않는 선언적 롤백 규칙을 제공
  - AOP 를 사용하여 트랜잭션 동작에 대한 커스터마이징을 지원(트랜잭션 롤백 수행 안에 특정한 동작을 지정할 수 있다)

- 글로벌 트랜잭션

  - 로컬 트랜잭션은 하나의 DB와의 트랜잭션인 반면, 글로벌 트랜잭션은 여러 개의 DB작업을 하나의 트랜잭션으로 묶어준다

  - 자바는 글로벌 트랜잭션을 위해 JTA(Java Transaction API)를 제공

  - JTA를 이용한 트랜잭션 처리 코드의 구조

    ![global_transaction.png](https://github.com/ratm8731/spring_study/blob/master/img/global_transaction.png?raw=true)

  - 스프링의 트랜잭션 추상화

    ![spring_global.png](https://github.com/ratm8731/spring_study/blob/master/img/spring_global.png?raw=true)