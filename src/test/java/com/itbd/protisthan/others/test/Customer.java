package com.itbd.protisthan.others.test;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    String name;
    int age;
    public Customer(String name, int age) {}

    public static void main(String[] args) {
        Customer c1 = new Customer("J", 20);
        Customer c2 = new Customer("J", 20);

        Set<Customer> customers = new HashSet<>();
        customers.add(c1);
        customers.add(c2);
        System.out.println(customers.size());

        Set<String> names = new HashSet<>();
        names.add("J");
        names.add("J");
        System.out.println(names.size());
    }
}
