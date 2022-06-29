package com.epam.spring.homework2.beans;

public class BeanD extends BeanEntity {

    public BeanD(String name, int value) {
        super(name, value);
        System.out.println("[Constructor] Constructor BeanD");
    }

    public void initMethod() {
        System.out.println("Init method BeanD");
    }


    public void destroyMethod() {
        System.out.println("Destroy method BeanD");
    }
}
