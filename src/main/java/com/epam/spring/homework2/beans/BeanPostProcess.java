package com.epam.spring.homework2.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcess implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BeanEntity) {
            ((BeanEntity) bean).validateField();
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
