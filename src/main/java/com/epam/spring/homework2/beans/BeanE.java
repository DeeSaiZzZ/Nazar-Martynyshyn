package com.epam.spring.homework2.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanE extends BeanEntity {

    public BeanE() {
        System.out.println("[Constructor] Constructor BeanE");
    }

    @PostConstruct
    public void postConst() {
        System.out.println(">>> [PostConstBeanE] I'm constructed.");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println(">>> [PreDestroyBeanE] I'm alive, but after 1 second i will be destroyed!");
    }
}
