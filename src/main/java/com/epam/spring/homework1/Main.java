package com.epam.spring.homework1;

import com.epam.spring.homework1.config.BeansConfig;
import com.epam.spring.homework1.other.OtherBeanC;
import com.epam.spring.homework1.pet.Cheetah;
import com.epam.spring.homework1.pet.Pet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);

        //After init OtherBeanC will be injected BeanC
        OtherBeanC otherBeanC = (OtherBeanC) context.getBean("otherBeanC");
        otherBeanC.ifNotNull();

        //Print all pets
        Pet pets = context.getBean(Pet.class);
        pets.printPets();

        //By Type, was extracted bean with annotation @Primary, when we delete @Primary there will be exception
        System.out.println(context.getBean(Cheetah.class).getClass().getSimpleName() + " bean by type.");

        //By name
        System.out.println(context.getBean("getCheetah1", Cheetah.class).getClass().getSimpleName() + " bean by name - getCheetah1.");
        System.out.println(context.getBean("getCheetah2", Cheetah.class).getClass().getSimpleName() + " bean ny name - getCheetah2.");
    }
}