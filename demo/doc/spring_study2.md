# 2주차


 ## Bean LifeCycle
    

![](https://lh5.googleusercontent.com/OHPuQc49FDwLvjuz73lrf8iyswPzhwsp6DMeHWObtfdIz2vUEfzIhd7dHSgtoGgNaD4VXLJcIbXeE9wtfzR7WCmCAIRXABNgJpZz1LdSZRufrL3lA4i0BjZ1gLQsaV6Q3KQnk7Cg)

-   IoC 컨테이너가 제공하는 주요 기능 중 하나는 생성이나 소멸 같은 빈 라이프 사이클의 특정 시점에 통지를 받을 수 있게 빈을 생성
	- 초기화 이후 : 빈에 모든 프로퍼티 값을 설정하고 의존성 점검을 마치자마자 발생
	- 소멸 이전 이벤트 : 스프링이 빈 인스터스를 소멸시키기 바로 전에 발생
   -   빈의 스코프와 상관없이 초기화 라이프 사이클 콜백 메서드를 호출 하지만 빈의 소멸 라이프사이클 메서드는 호출하지 않음
    
-   라이프 사이클 이벤트(초기화, 소멸)
    > Bean 정의시 메소드 지정
	    
		   <bean  id="BBean"  class="com.spring.bean.BSimpleClass" 
		   init-method="init"  destroy-method="destroy"/>

    - xml설정 파일 <bean>에서 init-method 지정 시 Bean생성과 properties의존성 주입 후 지정한 메소드 호출
    - xml설정 파일 <bean>에서 destroy-method 지정 시 Bean소멸 전 콜백으로 지정한 메소드 호출
    - 어플리케이션이 스프링과 결합되지 않는 효과가 있지만 초기화가 필요한 모든 빈을 등록해야 함
    
		
  

	> 인터페이스 구현
    
	

	    public  class SampleClass implements  InitializingBean, DisposableBean{
		    @Override
		    public  void  afterPropertiesSet()  throws Exception {
			    System.out.println("BEAN 생성 및 초기화 afterPropertiesSet() 호출됨");
		    }
		    @Override
		    public  void  destroy()  throws Exception {
			    System.out.println("BEAN 생성 소멸 : destroy 호출됨");
		    }
	    }

	-  InitializingBean Interface를 구현하면 Spring 에서 Bean생성과 properties의존성 주입 후 콜백 afterPropertiesSet()을 호출
	-  DispoableBean Interface를 구현하면 Spring 에서 Bean소멸 전 콜백 destroy()를 호출
	- 모든 인스턴스 초기화 콜백을 한번에 지정할 수 있지만 애플리케이션을 스프링에 결합  
  

	>  JSR-250 어노테이션 지정
    
	    public  class SampleClass {
		    @PostConstruct
		    public  void init(){
			    System.out.println("BEAN 생성 및 초기화 : init() 호출됨");
		    }
		    @PreDestroy
		    public  void destroy(){
			    System.out.println("BEAN 생성 소멸 : destroy 호출됨");
		    }
	    }

	- @PostConstruct 어노테이션을 지정한 메소드를 Bean생성과 properties의존성 주입 후 콜백으로 호출
	- @PreDestroy 어노테이션을 지정한 메소드를 Bean 소멸 전 콜백으로 호출
	- 이식성이 중요하다면 초기화 메서드나 애너테이션을 사용
    

  

	> @Bean 어노테이션 지정
    
	    public  class SampleClass {
		    @Bean(initMethod =”init”)
		    public  void init(){
			    System.out.println("BEAN 생성 및 초기화 : init() 호출됨");
		    }
		    @Bean(destoryMethod =”destory”)
		    public  void destory(){
			    System.out.println("BEAN 소멸 : destory() 호출됨");
		    }
	    }

	> 셧다운 후크
    
	    public  class SampleClass {
		    public  static  void main(String[] args) {
			    GenericXmlApplicationContext appCtx =  new GenericXmlApplicationContext();
			    appCtx.load("classpath:META-INF/spring/app-context.xml");
			    appCtx.registerShutdownHook();
			    appCtx.refresh();
			    SampleClass bean = appCtx.getBean("simpleBean", SampleClass.class);
		    }
	    }

	-	스프링에서 소멸 콜백의 유일한 단점은 자동으로 호출되지 않는다는 점
	-	ApplicationContext의 모든 구상 클래스가 상속한 AbstractApplicationContext의 destroy() 메서드를 호출
    
	> 스프링에서 관리되는 Bean 내부에서 확인 방법
    
	   ### BeanNameAware Interface
	-	bean 내부에서 자신이 지정된 id, name을 확인 하는 경우 사용
		

		    public  class SampleClass implements  BeanNameAware{
			    private  String name;
			    @Override
			    public  void  setBeanName(String name) {
				    System.out.println("BeanNameAware.setBeanName() 호출 됨 : name = "  + name);
				    this.name = name;
			    }
			    public  String getName() {
				    return  this.name;
			    }
		    }
  

	### ApplicationContextAware Interface
    
	- bean은 자신의 인스턴스를 생성관리하는 ApplicationContext가 어떤 인스턴스인지 확인하고 접근
    
		    public  class SampleClass implements  ApplicationContextAware{
			    @Override
			    public  void  setApplicationContext(ApplicationContext appCtx) throws BeansException {
				    try {
					    if(appCtx instanceof GenericXmlApplicationContext) {
						    System.out.println( "appCtx is GenericXmlApplicationContext true" );
						    // ApplicationContext 설정 수행
						    ((GenericXmlApplicationContext)appCtx).registerShutdownHook();
					    }
				    } catch (BeansException e) {
					    throw e;
				    }
			    }
		    }  

## FactoryBean
    

-   스프링은 기본으로 제공되는 스프링 구문(semantic)으로는 생성 및 관리할 수 없는 객체를 관리하기 위한 어댑터인 FactoryBean 인터페이스를 제공
    
-   Spring의 일반적인 DI패턴으로 생성하기 힘든 객체를 Spring에서 생성/관리하고자 할 때 Wrapping 기능 제공( 타 객체에 의존성 주입 등 )
    
-   Spring을 대신하여 사용자가 getObject를 구현하고 적절히 Instance를 생성하여 리턴하여 준다.
    

> FactoryBean Interface 구현체 작성하여 하위 Method 작성

>  application-context.xml

    <bean  id="hiFactoryBean"  class="com.spring.factory.MessageFactoryBean">
	    <property  name="type">
		    <value>Hi</value>
	    </property>
    </bean>
    <bean  id="helloFactoryBean"  class="com.spring.factory.MessageFactoryBean">
	    <property  name="type">
		    <value>Hello</value>
	    </property>
    </bean>
    <bean  id="messageRenderrer"  class="com.message.render.MessageRederrer">
	    <property  name="hiProvider">
		    <ref  bean="hiFactoryBean"/>
	    </property>
	    <property  name="helloProvider">
		    <ref  bean="helloFactoryBean"/>
	    </property>
    </bean>    

>  [IMessageProvider Interface] : getMessage() 메소드 정의 인터페이스

    public  interface IMessageProvider {
	    public  String getMessage();
    }

> [HiMessageProvider Class] : IMessageProvider를 구현하여 getMessage()에서 "Hi World"를 리턴

    public  class HiMessageProvider implements IMessageProvider{
	    private  static HiMessageProvider instance =  new HiMessageProvider();
	    private HiMessageProvider() {}
		@Override
		public  String getMessage() {
			return  "HI world";
		}
		public  static IMessageProvider getInstance() {
			return instance;
		}
    }

>  [HelloMessageProvider Class] : IMessageProvider를 구현하여 getMessage()에서 "Hello World"를 리턴

    public  class HelloMessageProvider implements IMessageProvider{
	    private  static HelloMessageProvider instance =  new HelloMessageProvider();
	    private HelloMessageProvider(){}
	    @Override
	    public  String getMessage() {
		    return  "Hello World";
	    }
	    public  static IMessageProvider getInstance() {
		    return instance;
	    }
    }

> MessageFactoryBean Class
	- FactoryBean Interface를 구현하여 getObject(), getObjectType(), isSingleton()을 구현
	- String type Property에 주입되는 값에 따라 getObject() 메소드에서 IMessageProvider 객체를 리턴

    public  class MessageFactoryBean implements  FactoryBean<IMessageProvider> {
	    private  String type;
	    @Override
	    public IMessageProvider getObject()  throws Exception {
		    if("Hi".equals(type)) 
			    return  HiMessageProvider.getInstance();
		    else
			    return  HelloMessageProvider.getInstance();
	    }
	    @Override
	    public Class<IMessageProvider>  getObjectType() {
		    return IMessageProvider.class;
	    }
	    @Override
	    public  boolean  isSingleton() {
		    return  true;
	    }
	    public  void setType(String type) {
		    this.type = type;
	    }
    }

> MessageRenderrer Class
	- FactoryBean Interface로 생성된 bean이 어떻게 다른 bean에 주입되는지 확인하기 위해 작성한 Renderrer Class

    public  class MessageRederrer {
	    private IMessageProvider hiProvider;
	    private IMessageProvider helloProvider;
	    public  void printMessage() {
		    System.out.println(hiProvider.getMessage());
		    System.out.println(helloProvider.getMessage());
	    }
	    public IMessageProvider getHiProvider() {
		    return hiProvider;
	    }
	    public  void setHiProvider(IMessageProvider hiProvider) {
		    this.hiProvider = hiProvider;
	    }
	    public IMessageProvider getHelloProvider() {
		    return helloProvider;
	    }
	    public  void setHelloProvider(IMessageProvider helloProvider) {
		    this.helloProvider = helloProvider;
	    }
    }

> MainApplication Class

    public  class MainApplication {
	    public  static  void main(String[] args) {
		    GenericXmlApplicationContext appCtx =  new GenericXmlApplicationContext();
		    appCtx.load("classpath:META-INF/spring/app-context.xml");
		    appCtx.refresh();
		    System.out.println("****************************************************");
		    // FactoryBean.getObject에서리턴 된 객체가 넘어온다.
		    IMessageProvider hiProvider = (IMessageProvider)appCtx.getBean("hiFactoryBean");
		    IMessageProvider helloProvider = (IMessageProvider)appCtx.getBean("helloFactoryBean");
		    System.out.println(hiProvider.getMessage());
		    System.out.println(helloProvider.getMessage());
		    /*//FacoryBean을 직접 가져오는 경우 id 앞에 & 를 붙여준다.
		    MessageFactoryBean hiFactoryBean = (MessageFactoryBean)appCtx.getBean("&hiFactoryBean");
		    MessageFactoryBean helloFactoryBean = (MessageFactoryBean)appCtx.getBean("&helloFactoryBean");
		    */
		    System.out.println("****************************************************");
		    // FacrotyBean을 직접 별도 클래스에 주입하여 사용하는 경우.
		    MessageRederrer render = (MessageRederrer )appCtx.getBean("messageRenderrer");
		    render.printMessage();
	    }
    }

- getObject() : Instance를 생성하거나 획득하여 Spring에서 관리할 객체를 리턴하여 준다.
- getType() : getObject()에서 리턴되는 객체의 타입을 명시하여 준다 (null이여도 상관없지만, 타입을 명시 했을 때 type기반 Autowired 가능)
- isSingleton() : getObject()에서 리턴되는 객체는 singleton인지 true/false를 리턴한다.
- bean definition 시 해당 factorybean Interface를 구현한 class를 참조하는 bean을 정의하고 일반 bean 처럼 사용하면 된다.
- applicationContext.getBean("factoryBean"); 수행시 FactoryBean 인터페이스의 구현체가 아닌 FactoryBean.getObjec()에서 리턴되는 객체가 리턴된다.
- applicationContext.getBean("&factoryBean"); 수행시 FactoryBean 자체가 리턴된다. (id 앞에 &를 붙인다)


> factory-bean / factory-method
    
   -  FactoryBean Interface 와 개념 및 용도 동일
   - 구현 방법의 차이
    
		>  application-context.xml	
    
		    <bean  id="hiFactoryBean"  class="com.spring.factory.MessageFactoryBean">
			    <property  name="type">
				    <value>Hi</value>
			    </property>
		    </bean>
		    <bean  id="helloFactoryBean"  class="com.spring.factory.MessageFactoryBean">
			    <property  name="type">
				    <value>Hello</value>
			    </property>
		    </bean>
		    <bean  id="hiProvider"  factory-bean="hiFactoryBean"  factory-method="getInstance"/>
		    <bean  id="helloProvider"  factory-bean="helloFactoryBean"  factory-method="getInstance"/>
		    <bean  id="messageRenderrer"  class="com.message.render.MessageRederrer">
			    <property  name="hiProvider">
				    <ref  bean="hiProvider"/>
			    </property>
			    <property  name="helloProvider">
				    <ref  bean="helloProvider"/>
			    </property>
		    </bean>

 
		> MessageFactory Class
    
		    public  class MessageFactoryBean {
			    private  String type;
				public IMessageProvider getInstance() {
					if("Hi".equals(type))
						return HiMessageProvider.getInstance();
					else
						return HelloMessageProvider.getInstance();
				}
				public  void setType(String type) {
					this.type = type;
				}
			}

	
	  > MainApplication Class
    
		    public  static  void main(String[] args) {
			    GenericXmlApplicationContext appCtx =  new GenericXmlApplicationContext();
			    appCtx.load("classpath:META-INF/spring/app-context.xml");
			    appCtx.refresh();
			    MessageRederrer render = (MessageRederrer )appCtx.getBean("messageRenderrer");
			    render.printMessage();
			    appCtx.close();
		    }

  

## 자바빈 PropertyEditor
    

-   PropertyEditor는 프로퍼티 값을 원래 자료 타입에서 String으로 변환하거나 반대로 String을 원래 타입으로 변환하는 인터페이스
    
-   PropertyEditor는 스프링으로 작업하는 데 편리한 기반이 되며, 적절한 PropertyEditor를 사용하면 파일이나 URL 같은 공통 컴포넌트를 사용하는 애플리케이션 구성 작업을 간단하게 할 수 있다
    
-   커스텀 PropertyEditor의 등록과 사용 가능
    
> 내장 PropertyEditor

| PropertyEditor  | 설명 |
|--|--|
| ByteArrayPropertyEditor  | 문자열을 상응하는 바이트 배열로 바꾼다. |
|CharacterEditor| 문자열에서 Character나 char 타입 값을 꺼낸다. |
|ClassEditor| 패키지를 포함하는 클래스 이름을 Class 인스턴스로 바꾼다. 이 PropertyEditor를 사용할 때는 클래스 이름 양 끝으로 위험하게 공백이 붙지 않도록 조심해야 하는데, 내부적으로 GenericXmlApplicationContext를 사용해 인스턴스를 얻을 때 ClassNotFoundException이 발생할 수 있기 때문이다. |
CustomBooleanEditor | 문자열을 자바 Boolean 타입으로 바꾼다.
CustomCollectionEditor | 특정 타입의 소스 컬렉션(예를 들어 구성 파일에 스프링의 util 네임스페이스를 사용해 표현된 Collection)을 대상 Collection 타입으로 변환한다.
CustomDateEditor| 날짜를 표현한 문자열을 java.util.Date로 바꾼다. 스프링의 ApplicationContext에 원하는 날짜 표시 타입을 가진 CustomDateEditor 구현체를 등록해야 한다.
FileEditor | 문자열로 된 파일 경로를 File 인스턴스로 바꾼다. 스프링이 파일이 존재하는지 여부를 체크하지는 않는다.
InputStreamEditor | 리소스를 나타내는 문자열(예를 들어 file:D:/temp/test.txt나 classpath: test.txt와 같은 파일 리소스)을 InputStream 프로퍼티로 바꾼다.
LocaleEditor | 로케일을 나타내는 ko_KR 같은 문자열을 java.util.Locale 인스턴스로 바꾼다.
PatternEditor | 문자열을 JDK Pattern 객체로 변환한다. 또는 그 반대로 컴파일된 Pattern에서 문자열 패턴을 얻는다.
PropertiesEditor | key1=value1 key2=value2 keyn=valuen의 형태로 이뤄진 문자열을 해당 프로퍼티가 설정된 java.util.Properties 인스턴스로 바꾼다.
StringTrimmerEditor | 문자열 주입 전에 문자열 앞뒤에 붙은 공백이나 탭과 같은 화이트스페이스를 제거한다. 명시적으로 이 PropertyEditor를 등록해야 한다.
URLEditor | 문자열로 나타낸 URL을 java.net.URL 인스턴스로 바꾼다.

 > SampleBean Class
    
    public  class SampleBean {
	    private  byte[] bytes; // byteArrayPropertyEditor
	    private Class cls; // ClassEditor
	    private Boolean trueOrFalse; // CustomBooleanEditor
	    private List<String> stringList; // CustomCollectionEditor
	    private Date date; // CustomDateEditor
	    private Float floatValue; // CustomNumberEditor
	    private File file; // CustomFileEditor
	    private InputStream stream; // InputStreamEditor
	    private Locale locale; // LocaleEditor
	    private Pattern pattern; // PatternEditor
	    private Properties properties; // PropertiesEditor
	    private  String trimString; // StringTrimmerEditor
	    private URL url; // URLEditor
	    public  void setBytes(byte[] bytes) {
		    System.out.println("Adding "  + bytes.length  +  "bytes");
		    this.bytes = bytes;
	    }
	    public  void setCls(Class cls) {
		    System.out.println("Setting class : "  + cls.getName());
		    this.cls = cls;
	    }
	    public  void setTrueOrFalse(Boolean trueOrFalse) {
		    System.out.println("Setting Boolean : "  + trueOrFalse);
		    this.trueOrFalse = trueOrFalse;
	    }
	    public  void setStringList(List<String> stringList) {
		    System.out.println("Setting string list with size : "  + stringList.size());
		    for(String s : stringList) {
			    System.out.println("String member : "  + s);
		    }
		    this.stringList = stringList;
	    }
	    public  void setDate(Date date) {
		    System.out.println("Setting date : "  + date);
		    this.date = date;
	    }
	    public  void setFloatValue(Float floatValue) {
		    System.out.println("Setting float value : "  + floatValue);
		    this.floatValue = floatValue;
	    }
	    public  void setFile(File file) {
		    System.out.println("Setting file : "+ file.getName());
		    this.file = file;
	    }
		public  void setStream(InputStream stream) {
			System.out.println("Setting stream : "+ stream);
			this.stream = stream;
		}
		public  void setLocale(Locale locale) {
			System.out.println("Setting Locale : "  + locale.getDisplayName());
			this.locale = locale;
		}
		public  void setPattern(Pattern pattern) {
			System.out.println("Setting pattern : "  + pattern);
			this.pattern = pattern;
		}
		public  void setProperties(Properties properties) {
			System.out.println("Loaded "  + properties.size() +  "properties");
			this.properties = properties;
		}
		public  void setTrimString(String trimString) {
			System.out.println("Setting trim string : "  + trimString);
			this.trimString = trimString;
		}
		public  void setUrl(URL url) {
			System.out.println("Setting URL : "  + url.toExternalForm());
			this.url = url;
		}
	}

> app-context.xml

    <bean  id="sampleBean"  class="com.javaking.bean.SampleBean">
    <!-- property type에 맞게 알아서 PropertyEditor가 동작한다. -->
    <property  name="date">
	    <value>2016-01-04</value>
    </property>
    <property  name="trimString">
	    <value>String need trimming</value>
    </property>
    <property  name="bytes">
	    <value>Hello World</value>
    </property>
    <property  name="cls">
	    <value>java.lang.String</value>
    </property>
    <property  name="trueOrFalse">
	    <value>true</value>
    </property>
    <property  name="stringList">
	    <util:list>
		    <value>String member 1</value>
		    <value>String member 2</value>
	    </util:list>
    </property>
    <property  name="floatValue">
	    <value>123.45678</value>
    </property>
    <property  name="file">
	    <value>classpath:test.txt</value>
    </property>
	<property  name="stream">
		<value>classpath:test.txt</value>
	</property>
	<property  name="locale">
		<value>en_US</value>
	</property>
	<property  name="pattern">
		<value>a*b</value>
	</property>
	<property  name="properties">
		<value>
			name=foo
			age=19
		</value>
	</property>
	<property  name="url">
		<value>http://javaking.tistory.com</value>
	</property>
	</bean>

  

> CustomPropertyEditor
						
> 	Human Class

    public  class Human {
	    int age; // 나이
	    String name; // 이름
		public Human(){}
		public Human(String name, int age) {
			this.age = age;
			this.name = name;
		}
		public  void setAge(int age) {
			this.age = age;
		}
		public  void setName(String name) {
			this.name = name;
		}
		public  int getAge() {
			return age;
		}
		public  String getName() {
			return name;
		}
	}

  > SampleJavaBean Class
 
 

    public  class  SampleJavaBean {
	    private Human man;
	    public Human getMan() {
		    return man;
	    }
	    public  void setMan(Human man) {
		    this.man = man;
	    }
    }

 
> HumanPropertyEditor Class
    
    public  class HumanPropertyEditor extends PropertyEditorSupport {
	    @Override
	    public  void  setAsText(String text) throws IllegalArgumentException {
		    String[] strArr = text.split("\\s");
		    Human value =  new Human(strArr[0], Integer.parseInt(strArr[1]));
		    setValue(value);
	    }
    }

  > app-context.xml
    
    <bean  class="org.springframework.beans.factory.config.CustomEditorConfigurer>
	    <property  name="customEditors">
		    <map>
			    <!-- com.javaking.bean.Human 타입의 객체인 경우 -->
			    <entry  key="com.javaking.bean.Human">
				    <!-- HumanPropertyEditor를 통해 객체를 주입하십시오 -->
				    <bean  class="com.javaking.propertyeditor.HumanPropertyEditor"/>
			    </entry>
		    </map>
		</property>
	</bean>
	<bean  id="simple"  class="com.javaking.bean.SimpleJavaBean">
		<property  name="man"><value>이지수 31</value></property>
	</bean>
