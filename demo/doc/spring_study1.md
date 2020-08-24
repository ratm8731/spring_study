# 1주차

## Dependency

![](https://lh4.googleusercontent.com/blBYrM5maRD2sGr9E3QO9RfgckpqtcXLud5w4upUQsb8ng_PLX_CCJsrLNlymmz8oHQdH3riBnjlMP55fqEP7Ttj-7gNTzxsxWIKG-bC41E0CFhi8XrgQD-ogsH2XkjOBQDzamXy)

-   A가 B를 멤버 변수나 로컬 변수로 가지고 있거나 혹은 파라미터로 전달되거나 B의 메소드를 호출
    
-   A가 B를 의존한다
    
-   B가 변경이 되면 A는 B를 의존하고 있어서 영향을 받게 됨
    

## Dependency Inversion Principle(DIP)

![](https://lh4.googleusercontent.com/Ec1IU9XEuw-gvXnwLKgWjLzH95iCWWlIZ6YMQl7qLDUHm5J-FYonKV0jJp9YR6vJBC4iG2l5mpMR2gke3hFIlU8rUNWkzD3acUu8JD-gDy7hDll-XbrFzJsovhwu2XcefFPunaeB)

![](https://lh5.googleusercontent.com/pSKhVFWIeH_BX7ddAWgweH7QbQ7DjjZrxKUAagTR7dlx-xX4nndjUaFqcyELqx36MUvGLqXCxqDhMALeqbWEVDmSLwO3yclO-zZ8DzDDX8rxOCgXlsBmQjn_QjCwtkAABeEmkDBB)

![](https://lh4.googleusercontent.com/PHZBbTdp829iAw2bNiqK2uB0LcEdUe5i0AOcbtrbOSUwZAf8kvyDiWeWf1WRC1_isvQM7kWT7-FJoAJmJ8G_6yf8Y2DF3LNs-cqK8t9KE0inGZd6j-q9DvQRnAwypfj7745aagti)

-   Class들 간의 의존성 부패를 제거하기 위한 일반인 디자인 방법
    
-   실제 사용 관계는 바뀌지 않으며 추상을 매개로 메시지를 주고 받음으로써 관계를 최대한 느슨하게 만드는 원칙
    

![Applying Dependency Inversion Principle](https://lh6.googleusercontent.com/dcOIHTwsi4ioCUTdmCD2GRtUNeOw3YzIhUEewYwoHsdkr5YPoyrrUIfDFADlAVaGT7UHW45Dm2HThCH-a4U0UaMb3KFstv8lpeKmWUN-Xy1tNGtC9j2rs5_oPZxUMNKUiPeRrZgP)
  

> LightBulb.java

    public class LightBulb {
	    public void turnOn() {
		    System.out.println("LightBulb: Bulb turned on...");
	    }
	    public void turnOff() {
		    System.out.println("LightBulb: Bulb turned on...");
	    }
    }
	    
> ElectricPowerSwitch.java

    public class ElectricPowerSwitch {
	    public LightBulb lightBulb;
	    public boolean on;
	    public ElectricPowerSwitch(LightBulb lightBulb) {
		    this.lightBulb = lightBulb;
		    this.on = false;
	    }
	    public boolean isOn() {
		    return this.on;
	    }
	    public void press(){
		    boolean checkOn = isOn();
		    if (checkOn) {
				lightBulb.turnOff();
				this.on = false;
			} else {
				lightBulb.turnOn();
				this.on = true;
			}
		}
	}

-   의존성 문제를 DIP를 적용
    

> Switch.java

  

    public interface Switch {
	    boolean isOn();
	    void press();
    }

> Switchable.java

    public interface Switchable {
	    void turnOn();
	    void turnOff();
    }


> ElectricPowerSwitch.java

  

    public class ElectricPowerSwitch implements Switch {
	    public Switchable client;
	    public boolean on;
	    public ElectricPowerSwitch(Switchable client) {
		    this.client = client;
		    this.on = false;
	    }
		public boolean isOn() {
			return this.on;
		}
		public void press(){
			boolean checkOn = isOn();
			if (checkOn) {
				client.turnOff();
				this.on = false;
			} else {
				client.turnOn();
				this.on = true;
			}
		}
	}
 

> LightBulb.java

    public class LightBulb implements Switchable {
	    @Override
	    public void turnOn() {
		    System.out.println("LightBulb: Bulb turned on...");
	    }
	    @Override
	    public void turnOff() {
		    System.out.println("LightBulb: Bulb turned off...");
	    }
    }


> Fan.java

    public class Fan implements Switchable {
	    @Override
	    public void turnOn() {
		    System.out.println("Fan: Fan turned on...");
	    }
	    @Override
	    public void turnOff() {
		    System.out.println("Fan: Fan turned off...");
	    }
    }  

## Dependency Injection(DI)

 -   객체간의 의존성을 외부에서 주입
 > Constructor Injection(생성자 주입)
	   

    class MovieLister...
    public MovieLister(MovieFinder finder) {
	    this.finder = finder;
	}
	class ColonMovieFinder...
	public ColonMovieFinder(String filename) {
		this.filename = filename;
	}
	

 - 필요한 의존성을 모두 포함하는 클래스의 생성자필요한 의존성을 모두 포함하는 클래스의 생성자
 - 생성자를 통해 의존성 주입  

> Setter Injection(Setter 메소드를 통한 의존성 주입)
  
  

    class MovieLister...
    private MovieFinder finder;
    public void setFinder(MovieFinder finder) {
	    this.finder = finder;
    }
	
	class ColonMovieFinder...
	public void setFilename(String filename) {
		this.filename = filename;
	}
  
  - 의존성을 입력받는 setter를 만들어서 의존성 주입
      

>  Interface Injection(초기화 인터페이스를 이용한 의존성 주입) - 스프링에서 지원하지 않는 DI 방식
    
 

    public interface InjectFinder {
	    void injectFinder(MovieFinder finder);
    }
    class MovieLister implements InjectFinder...
    public void injectFinder(MovieFinder finder) {
	    this.finder = finder;
    }
    public interface InjectFinderFilename {
		void injectFilename (String filename);
	}
    class ColonMovieFinder implements MovieFinder, InjectFinderFilename......
	public void injectFilename(String filename) {
		this.filename = filename;
	}


- 의존성을 주입하는 함수를 포함한 인터페이스를 작성하고 이 인터페이스를 구현하도록 함으로써 실행시에 이를 통하여 의존성 주입

  

## Inversion of Control(IoC)

-   객체의 생성에서부터 생명 주기의 관리까지 모든 객체에 대한 제어권이 바뀌었다는 것을 의미
    
-   일반적인 프로그램에서 제어의 방향
    

![](https://lh6.googleusercontent.com/_IOSKR5cVbhHQ-_-25UF8ZMxeUFaJc36-w8HCRR4aoO36LSY4qB5nAAWqbyTheq0CThF4I33dErP8cb9ncjDmMmplr6Mc7mQXm-0SIoJSrYxvGS1I7c_av1dkPiQuIdSQLpK_EDb)

-   역전된 제어의 흐름
    
![](https://lh6.googleusercontent.com/hewySh3d7fZnUnbAdQtYlI8VOQvPQaHR2K_41iwL8wT2AyBF-U0-vqcdlW0GtFOY8ZHme-6YfSCQzxtVWjZmR_r-tynMFoyriQGpDo1SUirXIP2LVm2gUnktF_bi7ZyKk1V4vQT0)

## 스프링 IoC 컨테이너와 빈

-   빈(Bean)을 만들고, 의존성을 엮어주며, 제공해주는 역할을 수행
    
-   BeanFactory는 하나의 인터페이스
    
-   Application Context는 BeanFactory의 구현체를 상속받고 있는 인터페이스
    
-   ApplicationContext와 다양한 빈 설정 방법
	1.  ClassPathXmlApplicationContext로 빈과 의존관계 등을 XML 파일로 설정하는 방법이 있었고, 이 굉장한 번거로움을 피하고자 등장한 것이 component-scan 이다.
	2.  Java Config로 직접 빈 설정(XML과 같이 @ComponentScan 사용 가능)
    
-   Bean Scope
	- ApplicationContext에 생성된 빈들은 스코프를 가지고 있다. 아무 설정도 해주지 않았으면. default인 싱글톤 스코프를 가진다.
	- 스코프
		- 싱글톤 : 애플리케이션 전반에 걸쳐서, 해당 빈의 인스턴스가 오직 한개
		- 프로토타입 : 빈을 주입 받아올 때마다 새로운 빈이 생성된다.
	- scoped-proxy
		- 빈 선언부에 스코프 설정과 같이 함
		

			    @Bean
			    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
			    public class Proto {
			    }

		-	proxyMode를 설정해야함. default는 프록시를 사용하지 않는다는 옵션.
		-	빈에 적용해야하므로 ScopedProxyMode.TARGET_CLASS를 프록시 모드로 설정한다.
		-	이 설정은, 클래스기반의 프록시로 감싸서 빈으로 등록하고, 다른 빈들이 사용할 때 프록시로 감싼 빈을 사용하게 하라는 설정이다.
		-	싱글톤 빈에서 프로토타입 빈을 사용하려면 이와 같은 설정을 하고, 싱글톤 빈이 프록시로 감싸진 빈을 참조하게 해서 매번 다른 빈을 참조하게 해야한다.
    

  

## 스프링 DI

> Constructor Injection
   - 생성자를 통한 전달
    
    public class Sample {
	    private final Example example;
	    @Autowired
	    public Sample(Example example) {
		    this.example = example;
	    }
    }

  

> Field Injection

    public class Sample {
			@Autowired
			private Example example;
	}    

-  사용하기도 간편하고 코드도 읽기 쉽다.  
- 추상적인 Injection 기법 때문에 Dependency 관계가 복잡해질 우려가 있어서 권장되지 않는다  

> Setter Injection

    public class Sample {
	    private Example example;
	    @Autowired
	    public void setExample(Example example) {
		    this.example = example;
	    }
    }
    
- 선택적인 의존성을 주입할 경우 유용
    
- Field Injection 으로 인한 패턴적 위험성을 상당 부분 해소
    
- Optional Injection 의 경우 권장되는 방식
  
  

## Anotation을 이용한 DI 설정

  
|  | @Autowired |  @Resource | @Inject
|--|--|--|--|
| 의존 | Spring | Java | Javax
| 사용 가능 위치| 필드, 생성자, setter| 필드, setter| 필드, 생성자, setter
| Bean 검색 우선 순위| 타입->이름 | 이름->타입| 타입->이름
| Bean 강제 지정| @Autowired|@Inject|@Resource(name=”ID”)
||@Qualifier(“ID”)| @Named(“ID”)| 
| Bean 없을 경우| @Autowired(required=false) 처리하면 예외 발생 방지| 예외 발생 | 예외 발생
 

> @Autowired
    
   - Type에 따라 알아서 Bean을 주입한다.
   -  필드, 생성자, 입력 파라미터가 여러 개인 메소드(@Qualifier는 메소드의 파라미터)에 적용 가능
   - Bean 객체가 없을 경우 발생하는 예외를 피하고 싶다면 required 속성값을 false
    

>  @Resource
    
   - 주입하려고 하는 객체의 이름(id)이 일치하는 객체를 자동으로 주입한다.
   - Setter에 붙일 수 있다. 생성자에는 붙일 수 없다.
   - @Autowired와 마찬가지로 반드시 기본 생성자가 정의되어 있어야 한다.
    

>  @Inject
    
- @Autowired와 유사하게 주입하려고 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다.
    

>  @Component
    
   -  @Component의 구체화된 형태
	   - @Controller
		   - Web MVC 코드에 사용되는 어노테이션이다.
		   - @RequestMapping 어노테이션을 해당 어노테이션 밑에서만 사용할 수 있다.
	   - @Service
		   - 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
		   - 다른 어노테이션과 다르게 @Component에 추가된 기능은 없다
	   - @Repository : 레포지토리의 역할 또는 스테레오 타입(=DAO)
	   - @Configuration : JavaConfig 설정
    
> @ComponentScan

- 어노테이션이 붙어있는 클래스의 위치를 기준으로 패키지, 그 이하 패키지에서 Component를 스캔
- 스캔 위치 설정 가능
	- basePackageClasses : 컴포넌트 스캔을 시작할 위치 설정
	- Filter : 어떤 어노테이션을 스캔할지 안 할지
