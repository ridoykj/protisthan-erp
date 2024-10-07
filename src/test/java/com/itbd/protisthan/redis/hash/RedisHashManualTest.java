package com.itbd.protisthan.redis.hash;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itbd.protisthan.config.redis.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataRedisTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(RedisConfig.class)
@Slf4j
public class RedisHashManualTest {

    // inject the actual operations
    @Autowired
    private RedisOperations<String, Person> operations;

    @Test
    public void putData() {
        Person user = new Person("fsdf", "Ridoy", "Kumar Joy");
        operations.opsForList().leftPush("person:ridoy", user);
    }

    @Test
    public void getData() {
        List<Person> persons = operations.opsForList().range("person:ridoy", 0, -1);
        persons.forEach(person -> {
            System.out.println(person.toString());
        });
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    record Person(String id, String firstName, String lastName) {
    }
}
