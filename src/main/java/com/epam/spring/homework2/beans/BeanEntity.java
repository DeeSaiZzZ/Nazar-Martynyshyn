package com.epam.spring.homework2.beans;

public abstract class BeanEntity {

    protected String name;
    protected int value;

    public BeanEntity() {

    }

    public BeanEntity(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "BeanEntity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public void validateField() {
        System.out.print("[Validator] ");
        if (name == null || value < 0) {
            System.out.println(this.getClass().getSimpleName() + " has a no valid field");
        } else {
            System.out.println("Field valid");
        }
    }
}
