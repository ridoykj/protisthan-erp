package com.itbd.protisthan.controller;

import com.itbd.protisthan.db.dto.redis.SellQueueDto;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.Nonnull;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;

import java.util.List;

@BrowserCallable // Indicates that this class contains methods that can be called from the browser
@AnonymousAllowed // Allows anonymous access to the methods in this class
@Slf4j // Enables logging for this class
public class SellQueueController {

    @Resource(name = "redisTemplate")
    private ListOperations<String, SellQueueDto> listOps;

    public void putInQueue(SellQueueDto order) {
        listOps.leftPush(String.format("sell:queue:%s:%s", order.employeeId(), order.customerId()), order.toModify());
    }

    public List<@Nonnull SellQueueDto> getQueueList(SellQueueDto order) {
        return listOps.range(String.format("sell:queue:%s:%s", order.employeeId(), order.customerId()), 0, -1);
    }
}
