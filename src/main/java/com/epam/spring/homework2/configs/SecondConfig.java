package com.epam.spring.homework2.configs;

import com.epam.spring.homework2.beans.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(basePackageClasses = {BeanFactoryPostProcess.class, BeanPostProcess.class})
public class SecondConfig {

    @Bean
    public BeanA getBeanA() {
        return new BeanA();
    }

    @Bean
    public BeanE getBeanE() {
        return new BeanE();
    }

    @Bean
    @Lazy
    public BeanF getBeanF() {
        return new BeanF();
    }
}
