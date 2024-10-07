package com.itbd.protisthan.redis.list;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;

import java.util.List;

@SpringBootTest
//@DataRedisTest
//@ExtendWith(SpringExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
//@SpringBootTest(classes = {RedisTest.class, RedisConfig.class})
public class RedisListTest {

    @Resource(name = "redisTemplate")
    private ListOperations<String, User> listOps;

    @Test
    public void putData() {
        User user = new User("fsdf", "Ridoy Kumar Joy", 27, "ridoykj@gmail.com");
        listOps.leftPush("user:ridoy", user);
    }

    @Test
    public void getData() {
        List<User> users = listOps.range("user:ridoy", 0, -1);
        users.forEach(u -> {
            System.out.println(u.toString());
        });
    }

    @Test
    public void updateData() {
        User user = new User("fsdf", "Ridoy Kumar Joy - update", 27, "ridoykj@gmail.com");
        listOps.set("user:ridoy", 0, user);
    }

    @Test
    public void deleteData() {
        User user = new User("fsdf", "Ridoy Kumar Joy - update", 27, "ridoykj@gmail.com");
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    record User(String id, String name, int age, String email) {
    }
}
