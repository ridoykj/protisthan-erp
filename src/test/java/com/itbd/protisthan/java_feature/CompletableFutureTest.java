package com.itbd.protisthan.java_feature;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CompletableFutureTest {

    @Test
    public void test1() {
        log.info("test1");

        Runnable th = () -> {
            int a = 1, b = 3;
            int sum = a + b;
            log.info("result = %d + %d = %d\n".formatted(a, b, sum));
        };
//        Thread t1 = new Thread(th);
        th.run();
    }

}
