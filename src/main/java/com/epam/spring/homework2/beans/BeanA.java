package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanA extends BeanEntity implements InitializingBean, DisposableBean {

    public BeanA() {
        System.out.println("[Constructor] Constructor BeanA");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println(">>> [AfterPropertiesSetBeanA] Set name = Mr.AfterProperSetBeanA and value = 22");
        this.setName("Mr.AfterProperSetBeanA");
        this.setValue(22);
    }

    @Override
    public void destroy() {
        System.out.println(">>> [DestroyBeanA] Destroy method in BeanA work!");
        System.out.println("I'm " + this.getName() + " destroyed!");
    }
}
