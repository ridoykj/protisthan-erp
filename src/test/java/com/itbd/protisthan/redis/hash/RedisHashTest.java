package com.itbd.protisthan.redis.hash;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itbd.protisthan.config.redis.RedisConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@DataRedisTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(RedisConfig.class)
@Slf4j
public class RedisHashTest {

    @Resource(name = "redisTemplate")
    private HashOperations<String, byte[], byte[]> listOps;

    HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

    @Test
    public void putData() {
        Person user = new Person("fsdf", "Ridoy", "Kumar Joy");
        Map<byte[], byte[]> mappedHash = mapper.toHash(user);
        listOps.putAll("person:ridoy", mappedHash);
    }

    @Test
    public void getData() {
        Map<byte[], byte[]> loadHash = listOps.entries("person:ridoy");
        Object persons = mapper.fromHash(loadHash);

        Person person  = (Person) persons;
        System.out.println(person);

//        loadHash.forEach(person -> {
//            System.out.println(person.toString());
//        });
    }
//
//    @Test
//    public void updateData() {
//        Person user = new Person("fsdf", "Ridoy - update", "Kumar Joy");
//        listOps.set("user:ridoy", 0, user);
//    }
//
//    @Test
//    public void deleteData() {
//        Person user = new User("fsdf", "Ridoy Kumar Joy - update", 27, "ridoykj@gmail.com");
//    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    record Person(String id, String firstName, String lastName) {
    }
}
