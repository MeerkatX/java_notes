## spring study

-------

重点**refresh**函数是核心必须要看

#### aop

[面向切面编程学习代码](./src/main/java/aop)

主要是以aspectJ注解为主

-------
#### ioc 
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

循环依赖三级缓存：

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

---------------------------
常用注解：

@Component @AutoWired @ComponentScan

-----------
想要成为一个框架，首先要考虑一定是扩展性

spring提供的扩展性有：

1、在对象创建前添加某些功能

2、在容器初始化前添加某些功能

3、在不同阶段发出不同的事件，完成一些事情

4、抽象出一堆接口来帮助扩展

5、面向接口编程

## spring-mvc

[code](./src/main/java/com/nuc/meerkatx/spmvc)