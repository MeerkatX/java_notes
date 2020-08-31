# spring

重点**refresh**函数是核心必须要看

体系结构：

![Spring 体系结构](readme.assets/arch1.png)

核心组件 bean、core、context 。Spring面向Bean的编程，BOP，把对象之间的依赖关系转而用配置文件、配置类来管理(**依赖注入**)

**Context**发现每个Bean之间的关系，为他们建立这种关系并且维护好这种关系。Context即Bean关系的集合 IOC容器

**Core**发现、建立、维护每个Bean之间关系所需要的一系列工具 Util。

## Bean

org.springframework.beans 主要解决 Bean定义、Bean的创建、Bean的解析

### Bean的创建

工厂方法

![image-20200807161957206](readme.assets/image-20200807161957206.png)

针对不同情况有不同的接口：

ListableBeanFactory 表示这些 Bean 是可列表的

AutoWireCapableBeanFactory 接口定义Bean的自动装配规则

HierarchicalBeanFactory 表示这些Bean是有继承关系的。

最终实现类：

DefaultListableBeanFactory

### Bean的定义

![image-20200807162657120](readme.assets/image-20200807162657120.png)

主要由BeanDefinition描述

## Context

## Core



## Spring IOC容器

### refresh 

梦开始的地方 AbstractApplicationContext 抽象类的 refresh方法

```java
public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {
    
    
    @Override
    public void refresh() throws BeansException, IllegalStateException {
       synchronized (this.startupShutdownMonitor) {
          // Prepare this context for refreshing.
          prepareRefresh();

          // Tell the subclass to refresh the internal bean factory.
          ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

          // Prepare the bean factory for use in this context.
          // 配置beanfactory，添加一些本身需要的工具类
          prepareBeanFactory(beanFactory);

          try {
             // Allows post-processing of the bean factory in context subclasses.
             // 注册实现了BeanPostProcessor接口的beans
             postProcessBeanFactory(beanFactory);

             // Invoke factory processors registered as beans in the context.
             // 初始化和执行 BeanFactoryPostProcessors beans
             invokeBeanFactoryPostProcessors(beanFactory);

             // Register bean processors that intercept bean creation.
             // 初始化和执行 bean processors beans
             registerBeanPostProcessors(beanFactory);

             // Initialize message source for this context.
             // 初始化 message source
             initMessageSource();

             // 初始化 event multicaster 
             // Initialize event multicaster for this context.
             initApplicationEventMulticaster();

             // 刷新子类实现的方法
             // Initialize other special beans in specific context subclasses.
             onRefresh();

             // 检测注册事件
             // Check for listener beans and register them.
             registerListeners();

             // 初始化 non-lazy-init 单例 bean
             // Instantiate all remaining (non-lazy-init) singletons.
             finishBeanFactoryInitialization(beanFactory);

             // Last step: publish corresponding event.
             // 执行LifecycleProcessor.onFresh和 ContextRefreshedEvent事件
             finishRefresh();
          }

          catch (BeansException ex) {
             if (logger.isWarnEnabled()) {
                logger.warn("Exception encountered during context initialization - " +
                      "cancelling refresh attempt: " + ex);
             }

             // Destroy already created singletons to avoid dangling resources.
             destroyBeans();

             // Reset 'active' flag.
             cancelRefresh(ex);

             // Propagate exception to caller.
             throw ex;
          }

          finally {
             // Reset common introspection caches in Spring's core, since we
             // might not ever need metadata for singleton beans anymore...
             resetCommonCaches();
          }
       }
    }
}
```

创建、刷新 BeanFactory

ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

```java
//AbstractApplicationContext
protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
   refreshBeanFactory();//调用子类实现的方法
   return getBeanFactory();//同样调用子类实现的方法
}
```

子类 AbstractRefreshableApplicationContext 和 GenericApplicationContext

子类 GenericApplicationContext

```java
public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {
    
    private final DefaultListableBeanFactory beanFactory;//保存BeanFactory
    
    public GenericApplicationContext() {
		this.beanFactory = new DefaultListableBeanFactory();
	}
    
    @Override
	public final ConfigurableListableBeanFactory getBeanFactory() {
		return this.beanFactory;//get方法
	}
    
    //refreshBeanFactory什么都不做，在最开始new的时候就已经实例化了，不再刷新了
    @Override
	protected final void refreshBeanFactory() throws IllegalStateException {
		if (!this.refreshed.compareAndSet(false, true)) {
			throw new IllegalStateException(
					"GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once");
		}
		this.beanFactory.setSerializationId(getId());
	}

}
```

另一个子类

AnnotationConfigWebApplicationContext的父类

```java
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    
    @Override
	public final ConfigurableListableBeanFactory getBeanFactory() {
		return this.beanFactory;//get方法
	}
    
    @Override
    protected final void refreshBeanFactory() throws BeansException {
       if (hasBeanFactory()) {//判断有没有实例化beanfactory
          destroyBeans();
          closeBeanFactory();
       }
       try {
          DefaultListableBeanFactory beanFactory = createBeanFactory();//没有的话创建
          beanFactory.setSerializationId(getId());
          customizeBeanFactory(beanFactory);
          loadBeanDefinitions(beanFactory);//加载、解析Bean的定义
          synchronized (this.beanFactoryMonitor) {
             this.beanFactory = beanFactory;
          }
       }
       catch (IOException ex) {
          throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
       }
    }
}
```

通过xml配置的applicationcontext

```java
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
       // Create a new XmlBeanDefinitionReader for the given BeanFactory.
       XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

       // Configure the bean definition reader with this context's
       // resource loading environment.
       beanDefinitionReader.setEnvironment(this.getEnvironment());
       beanDefinitionReader.setResourceLoader(this);
       beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

       // Allow a subclass to provide custom initialization of the reader,
       // then proceed with actually loading the bean definitions.
       initBeanDefinitionReader(beanDefinitionReader);
       loadBeanDefinitions(beanDefinitionReader);//重载
    }
    
    //Load the bean definitions with the given XmlBeanDefinitionReader.
    //加载类定义
    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
		Resource[] configResources = getConfigResources();
		if (configResources != null) {
			reader.loadBeanDefinitions(configResources);
		}
		String[] configLocations = getConfigLocations();
		if (configLocations != null) {
			reader.loadBeanDefinitions(configLocations);
		}
	}
}
```

通过设置类来配置ApplicationContext

```java
public class AnnotationConfigWebApplicationContext extends AbstractRefreshableWebApplicationContext
		implements AnnotationConfigRegistry {
    
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
       AnnotatedBeanDefinitionReader reader = getAnnotatedBeanDefinitionReader(beanFactory);
       ClassPathBeanDefinitionScanner scanner = getClassPathBeanDefinitionScanner(beanFactory);

       BeanNameGenerator beanNameGenerator = getBeanNameGenerator();
       if (beanNameGenerator != null) {
          reader.setBeanNameGenerator(beanNameGenerator);
          scanner.setBeanNameGenerator(beanNameGenerator);
          beanFactory.registerSingleton(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
       }

       ScopeMetadataResolver scopeMetadataResolver = getScopeMetadataResolver();
       if (scopeMetadataResolver != null) {
          reader.setScopeMetadataResolver(scopeMetadataResolver);
          scanner.setScopeMetadataResolver(scopeMetadataResolver);
       }

       if (!this.componentClasses.isEmpty()) {
          if (logger.isDebugEnabled()) {
             logger.debug("Registering component classes: [" +
                   StringUtils.collectionToCommaDelimitedString(this.componentClasses) + "]");
          }
          reader.register(ClassUtils.toClassArray(this.componentClasses));
       }

       if (!this.basePackages.isEmpty()) {
          if (logger.isDebugEnabled()) {
             logger.debug("Scanning base packages: [" +
                   StringUtils.collectionToCommaDelimitedString(this.basePackages) + "]");
          }
          scanner.scan(StringUtils.toStringArray(this.basePackages));
       }

       String[] configLocations = getConfigLocations();
       if (configLocations != null) {
          for (String configLocation : configLocations) {
             try {
                Class<?> clazz = ClassUtils.forName(configLocation, getClassLoader());
                if (logger.isTraceEnabled()) {
                   logger.trace("Registering [" + configLocation + "]");
                }
                reader.register(clazz);
             }
             catch (ClassNotFoundException ex) {
                if (logger.isTraceEnabled()) {
                   logger.trace("Could not load class for config location [" + configLocation +
                         "] - trying package scan. " + ex);
                }
                int count = scanner.scan(configLocation);
                if (count == 0 && logger.isDebugEnabled()) {
                   logger.debug("No component classes found for specified class/package [" + configLocation + "]");
                }
             }
          }
       }
    }
}
```

## aop

[面向切面编程学习代码](./src/main/java/aop)

主要是以aspectJ注解为主

## ioc 
[控制翻转学习代码](./src/main/java/ioc)

类定义
`Map<String,BeanDefinition> m1 = new ConcurrentHashMap<String,BeanDefinition>();`

类
`Map<String,Object> m2 = new ConcurrentHashMap<String,Object>();`


- BeanDefinitionReader 解析xml、注解、配置类中类的定义

- BeanFactory 生成bean实例的工厂方法

- BeanPostProcessor 在生成bean前后的处理（扩展）

- BeanFactoryPostProcessor

- IOC容器

### 循环依赖：

三级缓存机制

`DefaultSingletonBeanRegistry`

```java
    //一级缓存
	/** Cache of singleton objects: bean name to bean instance. */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    //三级缓存
	/** Cache of singleton factories: bean name to ObjectFactory. */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    //二级缓存
	/** Cache of early singleton objects: bean name to bean instance. */
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
```

首先创建A的实例化-> 将A 和 工厂（匿名内部类） 放到三级缓存 singletonFactories -> 开始解决依赖 B ，B没有再任何缓存中
那么B初始化->将B 和 工厂（匿名内部类） 放到三级缓存 -> 开始解决依赖A -> 发现A在三级缓存则调用工厂方法并放到二级缓存->从二级缓存获取A
后完成初始化，将B放到以及缓存（同时移除B的三级缓存）->获取B后将A放到一级缓存（同时移除A的二级缓存和三级缓存）

为什么必须三级缓存：

理论上二级缓存可以解决循环依赖问题，但是需要考虑代理问题AOP

在三级缓存中放置的是生成具体对象的一个匿名内部类，这个匿名内部类可能是代理类，也可能是普通的代理对象，而使用三级缓存就保证了不管是否
要代理对象，都保证使用的是一个对象，而不会出现，前面是普通bean，后面使用代理类

```java
//DefaultSingletonBeanRegistry

protected Object getSingleton(String beanName, boolean allowEarlyReference) {
		Object singletonObject = this.singletonObjects.get(beanName);
		if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
			synchronized (this.singletonObjects) {
				singletonObject = this.earlySingletonObjects.get(beanName);
				if (singletonObject == null && allowEarlyReference) {
					ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
					if (singletonFactory != null) {
						singletonObject = singletonFactory.getObject(); //三级缓存匿名内部类 ObjectFactory
						this.earlySingletonObjects.put(beanName, singletonObject); //放到二级缓存
						this.singletonFactories.remove(beanName); //从三级缓存中删除
					}
				}
			}
		}
		return singletonObject;
	}
```

## spring扩展性

1、在对象创建前添加某些功能

2、在容器初始化前添加某些功能

3、在不同阶段发出不同的事件，完成一些事情

4、抽象出一堆接口来帮助扩展

5、面向接口编程

## spring事务

事务属性：隔离级别、传播行为、回滚规则、是否只读、事务超时

[可能是最漂亮的Spring事务管理详解](https://juejin.im/post/6844903608224333838)

[全面分析 Spring 的编程式事务管理及声明式事务管理](https://developer.ibm.com/zh/articles/os-cn-spring-trans/)

spring事务主要有 编程式事务 和 声明式事务。

### 事务管理接口

- **PlatformTransactionManager：** （平台）事务管理器
- **TransactionDefinition：** 事务定义信息(事务隔离级别、传播行为、超时、只读、回滚规则)
- **TransactionStatus：** 事务运行状态

#### TransactionDefinition

```java
public interface TransactionDefinition {
    //事务的传播行为
	default int getPropagationBehavior() {
		return PROPAGATION_REQUIRED;// 0
        //	int PROPAGATION_NESTED = 6;
        //	int PROPAGATION_NEVER = 5;
        //	int PROPAGATION_NOT_SUPPORTED = 4;
        //	int PROPAGATION_REQUIRES_NEW = 3;
        //	int PROPAGATION_MANDATORY = 2;
        //	int PROPAGATION_SUPPORTS = 1;
	}
    //事务的隔离级别
    default int getIsolationLevel() {
		return ISOLATION_DEFAULT;// -1
         //int ISOLATION_READ_UNCOMMITTED = 1;
         //int ISOLATION_READ_COMMITTED = 2;
         //int ISOLATION_REPEATABLE_READ = 4;
         //int ISOLATION_SERIALIZABLE = 8;
	}
    //事务超时时间
    default int getTimeout() {
		return TIMEOUT_DEFAULT;// -1
	}
    //返回是否优化为只读事务
    default boolean isReadOnly() {
		return false;
	}
    //返回事务名称
    default String getName() {
		return null;
	}

}
```

#### PlatformTransactionManager 

用于执行具体的事务操作

```java
public interface PlatformTransactionManager extends TransactionManager {
	TransactionStatus getTransaction(@Nullable TransactionDefinition definition)
			throws TransactionException;//返回一个TransactionStatus对象，可能代表一个新的或者已经存在的事务
    void commit(TransactionStatus status) throws TransactionException;
    void rollback(TransactionStatus status) throws TransactionException;
}
```

#### TransactionStatus

```java
public interface TransactionStatus extends TransactionExecution, SavepointManager, Flushable {
	boolean hasSavepoint();
	@Override
    void flush();
}
```

### 事务传播行为

当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如：方法可能继续在现有事务中运行，也可能开启一个新事务，并在自己的事务中运行。

传播行为定义了被调用方法的事务边界

| 传播行为                      | 意义                                                    |
| ----------------------------- | ------------------------------------------------------------ |
| PROPERGATION_MANDATORY    | 表示方法必须运行在一个事务中，如果当前事务不存在，就抛出异常 |
| PROPAGATION_NESTED        | 表示如果当前事务存在，则方法应该运行在一个嵌套事务中。否则，它看起来和 PROPAGATION_REQUIRED看起来没什么俩样 |
| PROPAGATION_NEVER         | 表示方法不能运行在一个事务中，否则抛出异常               |
| PROPAGATION_NOT_SUPPORTED | 表示方法不能运行在一个事务中，如果当前存在一个事务，则该方法将被挂起 |
| PROPAGATION_REQUIRED      | 表示当前方法必须运行在一个事务中，如果当前存在一个事务，那么该方法运行在这个事务中，否则，将创建一个新的事务 |
| PROPAGATION_REQUIRES_NEW  | 表示当前方法必须运行在自己的事务中，如果当前存在一个事务，那么这个事务将在该方法运行期间被挂起 |
| PROPAGATION_SUPPORTS      | 表示当前方法不需要运行在一个是事务中，但如果有一个事务已经存在，该方法也可以运行在这个事务中 |

### 隔离级别

在操作数据时可能带来 3 个副作用，分别是脏读、不可重复读、幻读。为了避免这 3 中副作用的发生，在标准的 SQL 语句中定义了 4 种隔离级别，分别是未提交读、已提交读、可重复读、可序列化。而在 spring 事务中提供了 5 种隔离级别来对应在 SQL 中定义的 4 种隔离级别，如下：

| 隔离级别                   | 意义                                                    |
| ------------------------------ | ------------------------------------------------------------ |
| ISOLATION_DEFAULT          | 使用后端数据库默认的隔离级别                             |
| ISOLATION_READ_UNCOMMITTED | 允许读取未提交的数据（对应未提交读），可能导致脏读、不可重复读、幻读 |
| ISOLATION_READ_COMMITTED   | 允许在一个事务中读取另一个已经提交的事务中的数据（对应已提交读）。可以避免脏读，但是无法避免不可重复读和幻读 |
| ISOLATION_REPEATABLE_READ  | 一个事务不可能更新由另一个事务修改但尚未提交（回滚）的数据（对应可重复读）。可以避免脏读和不可重复读，但无法避免幻读 |
| ISOLATION_SERIALIZABLE     | 这种隔离级别是所有的事务都在一个执行队列中，依次顺序执行，而不是并行（对应可序列化）。可以避免脏读、不可重复读、幻读。但是这种隔离级别效率很低，因此，除非必须，否则不建议使用。 |

### 只读

事务的只读属性是指，对事务性资源进行只读操作或者是读写操作。所谓事务性资源就是指那些被事务管理的资源，比如数据源、 JMS 资源，以及自定义的事务性资源等等。如果确定只对事务性资源进行只读操作，那么我们可以将事务标志为只读的，以提高事务处理的性能。在 TransactionDefinition 中以 boolean 类型来表示该事务是否只读。

### 事务超时

所谓事务超时，就是指一个事务所允许执行的最长时间，如果超过该时间限制但事务还没有完成，则自动回滚事务。在 TransactionDefinition 中以 int 的值来表示超时时间，其单位是秒。

### 回滚原则

这些规则定义了哪些异常会导致事务回滚而哪些不会。默认情况下，事务只有遇到运行期异常时才会回滚，而在遇到检查型异常时不会回滚（这一行为与EJB的回滚行为是一致的）。 但是你可以声明事务在遇到特定的检查型异常时像遇到运行期异常那样回滚。同样，你还可以声明事务遇到特定的异常不回滚，即使这些异常是运行期异常。

### 事务实现

- DataSourceTransactionManager：适用于使用JDBC和iBatis进行数据持久化操作的情况。
- HibernateTransactionManager：适用于使用Hibernate进行数据持久化操作的情况。
- JpaTransactionManager：适用于使用JPA进行数据持久化操作的情况。
- 另外还有JtaTransactionManager 、JdoTransactionManager、JmsTransactionManager等等。

```java
public class DataSourceTransactionManager extends AbstractPlatformTransactionManager
		implements ResourceTransactionManager, InitializingBean {//java实现
    //模板方法中调用，AbstractPlatformTransactionManager调用
    @Override
	protected void doCommit(DefaultTransactionStatus status) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
		Connection con = txObject.getConnectionHolder().getConnection();
		if (status.isDebug()) {
			logger.debug("Committing JDBC transaction on Connection [" + con + "]");
		}
		try {
			con.commit();//调用数据库来提交
		}
		catch (SQLException ex) {
			throw new TransactionSystemException("Could not commit JDBC transaction", ex);
		}
	}
}
    
```

使用事务(原理) 底层原理：**动态代理** ：

```java
//获取数据库连接  
Connection con = DriverManager.getConnection()

//开启事务
con.setAutoCommit(true/false);

//执行数据操作（crud）

//提交事务/回滚事务 
con.commit() / con.rollback()

//关闭连接 
conn.close()
```



## 注解

**@Component**：这将 java 类标记为 bean。它是任何 Spring 管理组件的通用构造型。spring 的组件扫描机制现在可以将其拾取并将其拉入应用程序环境中。

**@Controller**：这将一个类标记为 Spring Web MVC 控制器。标有它的 Bean 会自动导入到 IoC 容器中。

**@Service**：此注解是组件注解的特化。它不会对 @Component 注解提供任何其他行为。您可以在服务层类中使用@Service 而不是 @Component，因为它以更好的方式指定了意图。

**@Repository**：这个注解是具有类似用途和功能的 @Component 注解的特化。它为 DAO 提供了额外的好处。它将 DAO 导入 IoC 容器，并使未经检查的异常有资格转换为 Spring DataAccessException。

**@Bean** 注解告诉Spring这个方法将会返回一个对象，这个对象要注册为Spring应用上下文中的bean。通常方法体中包含了最终产生bean实例的逻辑。

@Component（@Controller、@Service、@Repository）通常是通过类路径扫描来自动侦测以及自动装配到Spring容器中。

而@Bean注解通常是我们在标有该注解的方法中定义产生这个bean的逻辑。

```java
public class WireThirdLibClass {
    //可以搞一些自定义加载类
    @Bean
    public ThirdLibClass getThirdLibClass() {
        return new ThirdLibClass();
    }
}
```

## spring-mvc

[code](./src/main/java/com/nuc/meerkatx/spmvc)