# 3주차 

 ## AOP 개념
 - **AOP**는 **Aspect Oriented Programming**의 약자로 **관점 지향 프로그래밍**이라고 불린다. 
 - 관점 지향은 쉽게 말해 **어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화하겠다는 것이다**. 
 - 여기서 모듈화란 어떤 공통된 로직이나 기능을 하나의 단위로 묶는 것을 말한다.  
  ![enter image description here](https://blog.kakaocdn.net/dn/bMMwYu/btqz8ZGNTY0/zn9YlGXDRbAs9ap8To0C0k/img.png)
- AOP 핵심 개념
	- Aspect : 흩어진 관심사를 모듈화 한 것(Pointcut + Advice 조합)
	- Target : Aspect를 적용하는 곳( 클래스, 메서드,...)
	- Advice : 실질적으로 어떤 일을 해야할 지에 대한 것
	- Joinpoint : Advice가 적용될 위치, 끼어들 수 있는 지점
	- Pointcut : Joinpoint의 상세한 스펙을 정의한 것
	- Weaving : 포인트컷에 의해서 결정된 타겟의 조인 포인트에 부가기능(어드바이스)를 삽입하는 과정
	- Introduction : 추가 메서드나 필드를 도입해 객체의 구조를 수정하는 과정

## AOP의 종류
 > 정적 AOP
	
	 - 바이트코드 생성과 조작을 통해 AOP를 실현
	 - 에스펙트를 조금이라도 수정하게 되면 전체 어플리케이션을 다시 컴파일
	 - AspectJ의 컴파일 시점 위빙
 > 동적 AOP
 > 
	 - 어드바이스가 적용된 모든 객체에 대한 프록시를 생성해 필요에 따라 어드바이스를 호출
	 - 정적 AOP보다 성능이 좋지 않다
	 - 메인 어플리케이션 코드를 다시 컴파일하지 않아도 어플리케이션에서 전체 에스펙트를 쉽게 수정 가능
 ## 스프링의 AOP 지원
 - AOP alliance와 호환되는 방법으로 AOP를 지원
 > Agent Class

    public class Agent{
	    public void speak(){
		    System.out.print("Bond");
	    }
    }

 > AgentDecorator Class

    public class AgentDecorator implements MethodIntercepter {
	    public Object invoke(MethodInvocation invocation) throws Throwable {
		    System.out.print("James ");
		    Object retval = invocation.proceed();
		    System.out.println("!");
		    return retval; 
	    }
    }

> AgentAOPDemo Class

    public class AgentAOPDemo {
	    public static void main(String... args){
		    Agent target = new Agent();
		    ProxyFactory pf = new ProxyFactory();
		    pf.addAdvice(new AgentDecorator());
		    pf.setTarget(target);
			Agent proxy = (Agent) pf.getProxy();
			target.speak();
	    }
    }

 ## 스프링 AOP 아키텍처
 - 스프링 조인 포인트 
	 - 메서드 호출 조인포인트만 지원
 - 스프링 에스펙트
	 - Advisor 인터페이스를 구현한 클래스의 인스턴스
 - ProxyFactory 클래스
	 - 스프링 AOP의 위빙과 프록시 생성 과정을 제어
 - 스프링 어드바이스
	 - Before : target의 메소드 호출 전에 적용
	 
		> SecureBean Class
		
		    public class SecureBean {
			    public void writeSecureMessage() {
				    System.out.println("");
				}
			}
		> UserInfo Class

		    public class UserInfo {
				private String userName;
				private String password;
	
				public UserInfo(String username, String password) {
					this.userName = username;
					this.password = password;
				}

				public String getUserName() {
					return userName;
				}

				public String getPassword() {
					return password;
				}	
			}
	 - After returning : target의 메소드 호출 이후에 적용
		 > KeyGenerator Class

		    public class KeyGenerator {
				protected static final long WEAK_KEY = 0xFFFFFFF0000000L;
				protected static final long STRONG_KEY = 0xACDF03F590AE6L;
	
				private Random rand = new Random();
	
				public long getKey() {
					int x = rand.nextInt(3);
		
					if(x == 1) {
						return WEAK_KEY;
					}
		
					return STRONG_KEY;
				}
			}

		> WeakKeyCheckAdvice Class

		    public class WeakKeyCheckAdvice implements AfterReturningAdvice{

				public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
					// TODO Auto-generated method stub
					if((target instanceof KeyGenerator) && ("getKey".equals(method.getName()))) {
						long key  = ((Long)returnValue).longValue();
			
						if(key == WEAK_KEY) {
							throw new SecurityException("키 생성기가 취약 키를 생성 했습니다. 키를 다시 생성하기 바랍니다.");
				
						}
					}
				}
			}
	
		> AfterAdviceDemo Class
		
			public class AfterAdviceDemo {
				private static KeyGenerator getKeyGenerator() {
					KeyGenerator target = new KeyGenerator();
		
					ProxyFactory factory = new ProxyFactory();
					factory.setTarget(target);
					factory.addAdvice(new WeakKeyCheckAdvice());
		
					return (KeyGenerator) factory.getProxy();
				}
	
				public static void main(String[] args) {
					KeyGenerator keyGen = getKeyGenerator();
		
					for (int x = 0; x < 10; x++) {
						try {
							long key = keyGen.getKey();
							System.out.println("Key : " + key);
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("취약한 키가 생성 되었습니다.");
						}
					}
				}
			}
	 - After throwing : target의 예외 발생 후 적용
		 > ErrorBean Class

		    public class ErrorBean {
				public void errorProneMethod() throws Exception {
					throw new Exception("Generic Exception");
				}
	
				public void otherErrorProneMethod() throws IllegalArgumentException {
					throw new IllegalArgumentException("illegalArgument Exception");
				}
			}

		> SimpleThrowsAdvice	Class
			
			public class SimpleThrowsAdvice implements ThrowsAdvice{
				public static void main(String[] args) {
					ErrorBean errorBean = new ErrorBean();
					
					ProxyFactory factory = new ProxyFactory();
					factory.setTarget(errorBean);
					factory.addAdvice(new SimpleThrowsAdvice());
		
					ErrorBean proxy = (ErrorBean)factory.getProxy();
		
					try {
						proxy.errorProneMethod();
					} catch (Exception e) {
						// TODO: handle exception
					}
		
					try {
						proxy.otherErrorProneMethod();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
	
				public void afterThrowing(Exception ex) throws Throwable {
					System.out.println("***");
					System.out.println("Generic Exception Capture");
					System.out.println("Caught : "+ ex.getClass().getName());
					System.out.println("***\n");
				}		
	
				public void afterThrowing(Method method, Object args, Object target, 			IllegalArgumentException ex) throws Throwable {
					System.out.println("***");
					System.out.println("IllegalArgumentException Capture");
					System.out.println("Caught : "+ ex.getClass().getName());
					System.out.println("Method : "+ method.getName());
					System.out.println("***\n");
				}
			}
	 - Around : target의 메소드 호출 이전과 이후 모두 적용
		 > WorkerBean Class

		    public class WorkerBean {
				public void doSomeWork(int noOfTimes) {
					for (int i = 0; i < noOfTimes; i++) {
						work();
					}
				}
	
				private void work() {
					System.out.print("");
				}
			}

		> ProfilelingInterceptor Class

		    
			public class ProfilelingInterceptor implements MethodInterceptor{
				public Object invoke(MethodInvocation invocation) throws Throwable {
					StopWatch sw = new StopWatch();
					sw.start(invocation.getMethod().getName());
		
					Object returnValue = invocation.proceed();
					sw.stop();
					dumpInfo(invocation, sw.getTotalTimeMillis());
					return returnValue;
				}
	
				private void dumpInfo(MethodInvocation invocation, long totalTimeMillis) {
					// TODO Auto-generated method stub
					Method m = invocation.getMethod();
					Object target = invocation.getThis();
					Object[] args = invocation.getArguments();
		
					System.out.println("실행된 메서드 : "+ m.getName());
					System.out.println("객체 타입 : "+ target.getClass().getName());
					System.out.println("인수 : ");
					for (int i = 0; i < args.length; i++) {
						System.out.print("      > "+args+i);
					}
					System.out.print("\n");
					System.out.println("실행 시간 : "+ totalTimeMillis + " ms");
				}
			} 

## 스프링의 어드바이저와 포인트 컷
- 어드바이스 자체에서 어드바이스 검사 시 단점
	- 적용 가능한 메스드 목록을 어드바이스에 하드코딩하면 재사용성이 줄어듬
	- 메서드가 호출될 때 마다 검사를 해서 어플리케이션 성능 저하
- 포인트컷
	- 스프링의 포인트컷 모델은 어드바이스 타입과 독립적으로 포인트컷을 재사용
	> Pointcut Interface
	    
		public interface Pointcut {
		    ClassFilter getClassFilter();
		    MethodMatcher getMethodMatcher();
		}
	> ClassFilter Interface

		 
		public interface ClassFilter {
		    boolean matches(Class<?> clazz);
		}
    
    
	> MethodMatcher Interface

	    
		public interface MethodMatcher {
		    boolean matches(Method m, Class targetClass);
		    boolean isRuntime();
		    boolean matches(Method m, Class targetClass, Object[] args);
		}
		
		
- 정적 포인트컷
	- 스프링은 대상의 모든 메서드에 대해 한 번씩 MethodMatcher의 matches(method, Class <T>) 메서드를 호출하고 반환값을 캐싱해 나중에 메서드를 호출하는 데 사용
	- 각 메서드마다 한번만 포인트컷 적용 대상 여부를 검사하며 메서드를 나중에 호출하더라고 matches()를 호출하지 않음
	- 성능 자체는 정적 포인트컷이 좋음
- 동적 포인트컷
	- 메서드의 전반적인 적용 가능성을 결정하기 위해 메서드를 처음 호출 했을 때 matches(Method, Class <T>)를 사용하여 정적 검사를 하면 이외에도 정적 검사가 true를 반환하면 matches(Method, Class<T>, Object[]) 메서드를 이용해 추가 검사를 함
	- 불필요한 어드바이스 호출을 피하는 방법으로 사용하면 좋다

|  클래스 구현체| 설명 |
|--|--|
|  AnnotationMatchingPointcut|  특정 자바 애너테이션을 찾는 포인트컷 구현체|
|  AspectJExpressionPointcut|  AspectJ 구문으로 포인트컷 표현식을 평가|
|  ComposablePointcut|  둘 이상의 포인트컷을 Union()과 intersection() 같은 작업을 통해 하나로 구성하는데 사용|
|  ControlFlowPointcut|  다른 메서드의 제어 흐름에 포함되는 모든 메서드에 대응되는 특수한 포인트컷이며 다른 메서드의 호출 결과로부터 직간접적으로 호출되는 모든 메서드|
|  DynamicMethodMatcherPointcut|  동적 포인트컷을 생성할 때 사용하는 기반 클래스|
|  JdkRegexpMethodPointcut|  jdk 1.4 정규 표현식으로 포인트컷 정의|
|  NameMatchMethodPointcut|  메서드 이름 목록에 메서드가 포함되는지 간단히 확인하는 포인트컷|
|  StaticMethodMatcherPointcut|  정적 포인트컷을 생성할 때 사용할 수 있는 기반 클래스|
- StaticMethodMatcherPointcut(정적 포인트 컷 생성)
	> Singer Interface

	    public interface Singer{    
		    public void sing();
		}

	> GoodGuitarlist Class

	    
		public class GoodGuitarlist implements Singer{
			public void sing() {
				System.out.println("I'm a good guitarlist.");
			}
		}
	> GreatGuitarlist Class

	    public class GreatGuitarlist implements Singer{
			public void sing() {
				// TODO Auto-generated method stub
				System.out.println("I'm a great guitarlist.");
			}
		}
	> SimpleAdvice Class

		public class SimpleAdvice implements MethodInterceptor{
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println(">> invoking " + invocation.getMethod().getName());
				Object retVal = invocation.proceed();
				System.out.println(">> Done.\n");
				return retVal;
			}
		}

	> SimpleStaticPointcut Class
   
	    public class SimpleStaticPointcut extends StaticMethodMatcherPointcut{

			public boolean matches(Method method, Class<?> targetClass) {
				// TODO Auto-generated method stub
				return ("sing".equals(method.getName()));
			}
	
			@Override
			public ClassFilter getClassFilter() {
				return cls -> (cls == GoodGuitarlist.class);
			}
		}

	> StaticPointcutDemo Class

	    public class StaticPointcutDemo {
			public static void main(String[] args) {
				GoodGuitarlist johnMayer = new GoodGuitarlist();
				GreatGuitarlist ericClapton = new GreatGuitarlist();
		
				Singer proxyOne;
				Singer proxyTwo;
		
				Pointcut pc = new SimpleStaticPointcut();
				Advice advice = new SimpleAdvice();
				Advisor advisor = new DefaultPointcutAdvisor(pc, advice);
		
				ProxyFactory pf = new ProxyFactory();
				pf.addAdvisor(advisor);
				pf.setTarget(johnMayer);
				proxyOne = (Singer) pf.getProxy();
		
				pf = new ProxyFactory();
				pf.addAdvisor(advisor);
				pf.setTarget(ericClapton);
				proxyTwo = (Singer) pf.getProxy();
		
				proxyOne.sing();
				proxyTwo.sing();
			}
		}
- DynamicMethodMatcherPointcut(동적 포인트컷)
	> SampleBean Class

	    public class SampleBean {
			public void foo(int x) {
				System.out.println("invoked foo() with : "+x);
			}
			public void bar() {
				System.out.println("invoked bar() ");
			}
		}

	> SimpleDynamicPointcut Class

	    public class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut {
	
			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				System.out.println("Static check for "+ method.getName());
				return ("foo".equals(method.getName()));
			}
	
			@Override
			public boolean matches(Method method, Class<?> targetClass, Object... args) {
				System.out.println("Dynamic check for "+ method.getName());
				int x = ((Integer) args[0]).intValue();
				
				return (x != 100);
			}
	
			@Override
			public ClassFilter getClassFilter() {
				return cls -> (cls == SampleBean.class);
			}
		}

	> DynamicPointcutDemo Class

	    
		public class DynamicPointcutDemo {
			public static void main(String[] args) {
				SampleBean target = new SampleBean();
				Advisor advisor = new DefaultPointcutAdvisor(new SimpleDynamicPointcut(), 		new SimpleAdvice());
		
				ProxyFactory pf = new ProxyFactory();
				pf.setTarget(target);
				pf.addAdvisor(advisor);
				SampleBean proxy = (SampleBean) pf.getProxy();
		
				proxy.foo(1);
				proxy.foo(10);
				proxy.foo(100);
		
				proxy.bar();
				proxy.bar();
				proxy.bar();
			}
		}
## 프록시 이해하기
- JDK Dynamic Proxy: Java에서 기본적으로 제공하는 표준 프록시 생성 모듈. 인터페이스 기반의 프록시 생성  
	- 인터페이스만 프록시
- cglib: 오픈소스 라이브러리. 클래스 기반의 프록시 생성 
	- 클래스와 인터페이스 모두 프록시 가능
	- CGLIB 고정 모드로 사용하지 않는 한 JDK와  CGLIB 표준 모드에는 큰 차이가 없음
	- 고정 모드 시 어드바이스 체인을 변경하지 못하지만 설능을 최적화
 ## 인트로덕션 시작하기
 - 타입을 대신해 추가적인 메소드나 필드를 선언하는 것
 - 클래스 레벨에서만 적용
 > IsModified Interface
 
 

    
	public interface IsModified {
		boolean isModified();
	}
 > IsModifiedMixin Class
   
	public class IsModifiedMixin extends DelegatingIntroductionInterceptor implements IsModified{
		private boolean isModified = false;
		private Map<Method, Method> methodCache = new HashMap<>();
		@Override
		public boolean isModified() {
			// TODO Auto-generated method stub
			return isModified;
		}
	
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			if(!isModified) {
					if((invocation.getMethod().getName().startsWith("set")) && 	(invocation.getArguments().length == 1)) {
						Method getter = getGetter(invocation.getMethod());
						if(getter != null) {
							Object newVal = invocation.getArguments();
							Object oldVal = getter.invoke(invocation.getThis(), null);
							if(newVal == null && oldVal == null) {
								isModified = false;
							} else if (newVal == null && oldVal != null) {
								isModified = true;
							} else if(newVal != null && oldVal == null) {
								isModified = true;
							} else {
								isModified = !newVal.equals(oldVal);
							}
						}		
					}
			}
			return super.invoke(invocation);
		}
	
		private Method getGetter(Method setter) {
			Method getter = methodCache.get(setter);
			if(getter != null) {
				return getter;
			}
			String getterName = setter.getName().replaceFirst("set", "get");
			try {
				getter = setter.getDeclaringClass().getMethod(getterName, null);
				synchronized (methodCache) {
					methodCache.put(setter, getter);
				}
				return getter;
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}
	}
 > IsModifiedAdvisor Class

    public class IsModifiedAdvisor extends DefaultIntroductionAdvisor{
		public IsModifiedAdvisor() {
			super(new IsModifiedMixin());
		}
	}

 > Contact Class

    public class Contact {
		private String name;
		private String phoneNumber;
		private String email;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}

 > IntroductionDemo Class

    public class IntroductionDemo {
		public static void main(String[] args) {
			Contact target = new Contact();
			target.setName("test1");
		
			IntroductionAdvisor advisor = new IsModifiedAdvisor();
		
			ProxyFactory pf = new ProxyFactory();
			pf.setTarget(target);
			pf.addAdvisor(advisor);
			pf.setOptimize(true);
		
			Contact proxy = (Contact) pf.getProxy();
			IsModified proxyInterface = (IsModified) proxy;
		
			System.out.println("Is Contact ? : "+(proxy instanceof Contact));
			System.out.println("Is IsModified ? : "+(proxy instanceof IsModified));
		
			System.out.println("Has been modified? : "+proxyInterface.isModified());
			proxy.setName("test1");
			System.out.println("Has been modified? : "+proxyInterface.isModified());
			proxy.setName("test12");
			System.out.println("Has been modified? : "+proxyInterface.isModified());
		}
	}

 ## 인트로덕션 정리
 - 기존 메서드의 기능을 확장할 뿐만 아니라 인터페이스 및 객체 구현체를 동적으로 확장할 수 있음
 - 프록시를 통해 동작하므로 어느 정도 성능 오버헤드 발생
 ## AOP 프레임워크 서비스
- 선언적인 AOP 구성
	- ProxyFactoryBean 사용
	- 스프링 aop 네임스페이스 사용
	- @AspectJ 방식 애너테이션 사용
