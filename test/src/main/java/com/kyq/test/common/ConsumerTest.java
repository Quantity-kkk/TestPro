package com.kyq.test.common;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Description:
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/12/4
 */
public class ConsumerTest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean compare(String a, String b){

        return Predicate.isEqual(b).test(a);

    }

    public boolean testP(String a, Predicate predicate, Predicate p2){
        return predicate.or(p2).test(a);
    }

    public ConsumerTest set(Consumer<ConsumerTest> consumer){
        consumer.accept(this);
        return this;
    }

    public static void main(String[] args){
        ConsumerTest t = new ConsumerTest();
        t.setName("TD");
        System.out.println(t.getName());

        t.set(x->x.setName("TF")).set(x->x.setName("TB"));
        System.out.println(t.getName());

        System.out.println(t.testP("a",x->x.equals("a"),x->x.equals("c")));
//        System.out.println(t.compare("a","b"));
//        System.out.println(t.compare("a","a"));

    }

}
