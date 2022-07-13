package com.epam.spring.homework2.beans;

public abstract class BeanEntity {

    private String name;
    private int value;

    public BeanEntity() {
        //Constructor required for the BeanA class constructor to work
        System.out.println("[Constructor] Constructor BeanEntity");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
