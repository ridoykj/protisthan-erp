package com.itbd.protisthan.redis.list;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;

import java.util.List;

@SpringBootTest
//@DataRedisTest
//@ExtendWith(SpringExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
//@SpringBootTest(classes = {RedisTest.class, RedisConfig.class})
public class RedisListManualTest {

    // inject the actual operations
    @Autowired
    private RedisOperations<String, Object> operations;

    @Test
    public void putData() {
        User user = new User("fsdf", "Ridoy Kumar Joy", 27, "ridoykj@gmail.com");
        operations.opsForList().leftPush("user:ridoy", user);
    }

    @Test
    public void getData() {
        List<Object> users = operations.opsForList().range("user:ridoy", 0, -1);
        users.forEach(u -> {
            User user = (User) u;
            System.out.println(user.toString());
        });
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    record User(String id, String name, int age, String email) {
    }
}
