package com.epam.spring.homework2.beans;

public class BeanC extends BeanEntity {

    public BeanC(String name, int value) {
        super(name, value);
        System.out.println("[Constructor] Constructor BeanC");
    }

    public void initMethod() {
        System.out.println("Init method BeanC");
    }

    public void destroyMethod() {
        System.out.println("Destroy method BeanC");
    }
}
