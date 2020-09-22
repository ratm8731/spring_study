## 웹 소켓

  - 웹 소켓 프로토콜인 `RFC 6455`는 단일 TCP 연결을 통해 클라이언트와 서버 사이에 전이중 양방향 통신(*쌍방이 동시에 송신할 수 있는 것*) 채널을 설정하는 표준화된 방법

  - HTTP와는 **다른** TCP 프로토콜이지만 포트 80 및 443을 사용해 HTTP를 통해 작동

  - 기존 방화벽 규칙을 재사용할 수 있도록 설계

  - WebSocket은 HTTP 호환 가능하도록 설계

    ![](https://github.com/ratm8731/spring_study/blob/master/img/%EC%9B%B9%EC%86%8C%EC%BC%93.png?raw=true)

    ![웹 소켓](https://github.com/ratm8731/spring_study/blob/master/img/websocket.png?raw=true)

    ![지원 브라우저](https://github.com/ratm8731/spring_study/blob/master/img/support-browsers.png?raw=true)
    
    

## 스프링 웹플럭스

![웹플럭스](https://github.com/ratm8731/spring_study/blob/master/img/%EC%9B%B9%ED%94%8C%EB%9F%AD%EC%8A%A4.PNG?raw=true)

  - 웹 어플리케이션에서 리액티브 프로그래밍을 제공

  - 비동기-논블럭킹 리액티브 개발에 사용

  - 서비스간 호출이 많은 서비스에 적합

  - 스프링5는 Spring Boot 2 부터 도입

    ![스프링부트 웹 플럭스](https://github.com/ratm8731/spring_study/blob/master/img/spring_boot_webflux.png?raw=true)
    - Reactive Stack 를 사용할지, Servlet Stack 를 사용할지 선택(동시 사용 불가)