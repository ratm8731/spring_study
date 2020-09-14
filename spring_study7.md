## MVC 및 스프링 MVC 소개

MVC 패턴
  - 서로 다른 컴포넌트에 대한 책임이 명확한 아키텍처
  - 모델(Model) 
    - 애플리케이션의 정보, 데이타를 나타냅니다. 
    - 데이타베이스, 처음의 정의하는 상수, 초기화값, 변수 등을 뜻합니다. 
    - DATA, 정보들의 가공을 책임지는 컴포넌트
  - 뷰(View)
    - 다이얼로그에 존재하는 텍스트박스, 라벨, 버튼 등 사용자 인터페이스(User interface) 요소들을 의미
    - 사용자가 제어하고 데이터를 확인할 수 있는 영역
  - 콘트롤러(Controller)
    - 모델과 뷰를 관장하는 브릿지(Bridge)역할을 수행
    - 입력이 발생하면 이에 대한 통지를 담당

## Spring MVC Request Lifecycle의 구조

### Filter

![img](https://github.com/ratm8731/spring_study/blob/master/img/filter.png?raw=true)

- **Web Application의 전역적인 로직을 담당**한다.
- Filter라는 단어 뜻에서 알 수 있듯이, 전체적인 필터링(설정)을 하는 곳이라고 생각하면 된다.
- DispatcherServlet에 들어가기 전인 Web Application단에서 실행된다.

### DispatcherServlet

![img](https://github.com/ratm8731/spring_study/blob/master/img/dispatcherServlet.png?raw=true)

- 들어오는 **모든 Request를 우선적으로 받아 처리해주는 서블릿**이다.
- HandlerMapping에게 Request에 대해 매핑할 Controller 검색을 요청한다.
- HandlerMapping으로부터 Controller 정보를 반환받아 해당 Controller와 매핑시킨다.
- Dispatcher라는 단어가 '배치 담당자'라는 뜻이 있듯이, 말 그대로 Request에 대해 어느 컨트롤러로 매핑시킬것인지 배치하는 역할을 한다.

### HandlerMapping

![img](https://github.com/ratm8731/spring_study/blob/master/img/handlerMapping.png?raw=true)

- DispatcherServlet으로부터 **검색을 요청받은 Controller를 찾아 정보를 리턴**해준다.

### HandlerInterceptor

![img](https://github.com/ratm8731/spring_study/blob/master/img/handlerInterceptorimage.png?raw=true)

- **Request가 Controller에 매핑되기전 앞단에서 부가적인 로직을 끼워넣는다.**
- 주로 세션, 쿠키, 권한 인증 로직에 많이 사용된다.

### Controller

![img](https://github.com/ratm8731/spring_study/blob/master/img/controller.png?raw=true)

- Request와 매핑되는 곳이다.
- **Request에 대해 어떤 로직(Service)으로 처리할 것인지를 결정하고, 그에 맞는 Service를 호출**한다.
- Service Bean을 스프링 컨테이너로부터 주입받아야 한다. Service Bean의 메소드를 호출해야 하기 때문이다.

### Service

![img](https://github.com/ratm8731/spring_study/blob/master/img/service.png?raw=true)

- **데이터 처리 및 가공을 위한 비즈니스 로직을 수행**한다.
- Request에 대한 실질적인 로직을 수행하기 때문에, Spring MVC Request Lifecycle의 심장이라고 볼 수 있다. Service가 없다면 서버 애플리케이션의 존재 이유도 없다.
- Repository를 통해 DB에 접근하여 데이터의 CRUD(Create, Read, Update, Delete)를 처리하기도 한다.

### Repository

![img](https://github.com/ratm8731/spring_study/blob/master/img/repository.png?raw=true)

- **DB에 접근하는 객체**이다. DAO(Data Access Object) 라고 부른다.
- Service에서 DB에 접근할 수 있게 하여 데이터의 CRUD를 할 수 있게 해준다.

### ViewResolver

![img](https://github.com/ratm8731/spring_study/blob/master/img/viewResolver.png?raw=true)

- Controller에서 리턴한 View의 이름을 DispatcherServlet으로부터 넘겨받고, 해당 **View를 렌더링**한다.
- 렌더링한 View는 DispatcherServlet으로 리턴하고, DispatcherServlet에서는 해당 View 화면을 Response 한다.