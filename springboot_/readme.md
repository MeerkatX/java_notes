# SpringBoot

![](https://img.shields.io/badge/开箱即用-约定大于配置-blue.svg)

## Spring Boot 自动装配

> SpringBoot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值
>
> 将这些值作为自动配置类导入容器 ， 自动配置类就生效 ， 帮我们进行自动配置工作；
>
> 以前我们需要自己配置的东西 ， 自动配置类都帮我们解决了
>
> 整个J2EE的整体解决方案和自动配置都在springboot-autoconfigure的jar包中；
>
> **它将所有需要导入的组件以全类名的方式返回 ， 这些组件就会被添加到容器中 ；**
>
> 它会给容器中导入非常多的自动配置类 （xxxAutoConfiguration）, 就是给容器中导入这个场景需要的所有组件 ， 并配置好这些组件 ；
>
> **有了自动配置类 ， 免去了我们手动编写配置注入功能组件等的工作；**

## 自动配置

包spring-boot-autoconfigure下的spring.factories

## springboot整合redis

### RedisConfig

自己配置RedisConfig类，覆盖原有spring boot实现

```java
//自己写配置类
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        //...
    }
    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(){
        //...
    }
}
```

### 用法

springboot test中：

```java
   //springboot test
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        // redisTemplate
        // opsForValue操作字符串
        // opsForGeo操作Geo
        // ...5大数据类型，3大特殊类型都有对应
        redisTemplate.opsForValue().set("redis", "helloworld");
        System.out.println(redisTemplate.opsForValue().get("redis"));
//        redisTemplate.opsForGeo();

//        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
//        redisConnection.flushDb();
    }

    @Test
    void test() throws JsonProcessingException {
        User user = new User("xx", 1);
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user", user);//配置RedisConfig 序列化手段最后就可以直接序列化 user为json
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
```

spring.factories -> RedisAutoConfiguration -> RedisProperties

导入依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```
RedisProperties
```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
```
Jedis: 直连，多线程不安全。可以采用jedis pool连接池(但是是BIO的)

lettuce: 采用netty，实例可以再多个线程中进行共享，不存在线程不安全情况，可以减少线程

## 拦截器

### 拦截器基本使用

拦截器属于spring mvc中：

需要重写三个方法 preHandle postHandle afterCompletion

preHandle 在Controller前进行，一遍实际编写登录拦截等都是在这里执行。

```java
//通过实现HandlerInterceptor接口，编写拦截器
public class MyInterceptor implements HandlerInterceptor{
    
    //return true 放行，执行下一个拦截器，false 不执行拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
```

写好逻辑代码之后需要配置到spring boot中

```java
@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = new ArrayList<>();
        patterns.add("/hello/**");
        registry.addInterceptor(myInterceptor()).addPathPatterns(patterns);
    }
}
```

