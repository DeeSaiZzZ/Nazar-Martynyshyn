package com.epam.spring.homework2.beans;

public class BeanB extends BeanEntity {

    public BeanB(String name, int value) {
        super(name, value);
        System.out.println("[Constructor] Constructor BeanB");
    }

    public void initMethod() {
        System.out.println("Init method BeanB");
    }

    public void destroyMethod() {
        System.out.println("Destroy method BeanB");
    }

    public void secondInitMethod() {
        System.out.println("secondInitMethod BeanB");
    }
}
