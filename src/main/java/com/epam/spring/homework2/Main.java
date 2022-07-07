package com.epam.spring.homework2;

import com.epam.spring.homework2.beans.*;
import com.epam.spring.homework2.configs.FirstConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(FirstConfig.class);

        System.out.println("--------------------------------------------------------------------------");

        System.out.println(">>> All beans: ");
        Arrays.stream(appContext.getBeanDefinitionNames())
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------------------------------");

        System.out.println(">>> Beans name's and value's: ");
        System.out.println(appContext.getBean(BeanA.class));
        System.out.println(appContext.getBean(BeanB.class));
        System.out.println(appContext.getBean(BeanC.class));
        System.out.println(appContext.getBean(BeanD.class));
        System.out.println(appContext.getBean(BeanE.class));

        System.out.println("--------------------------------------------------------------------------");

        System.out.println(appContext.getBean(BeanF.class));

        System.out.println("--------------------------------------------------------------------------");

        appContext.close();
    }
}
