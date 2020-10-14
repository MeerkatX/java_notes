package com.nuc.meerkatx.springbootstudy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuc.meerkatx.springbootstudy.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringBootStudyApplicationTests {


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
        User user = new User();
        user.setName("xx");
        user.setAge(1);
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user", user);//配置RedisConfig 序列化手段最后就可以直接序列化 user为json
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
