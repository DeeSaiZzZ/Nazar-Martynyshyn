package com.epam.spring.homework1.other;

import com.epam.spring.homework1.beans.BeanC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OtherBeanC {

    @Autowired
    public BeanC beanC;

    //BeanC null because the injection will be after the bean is created
    public OtherBeanC() {
        System.out.println(beanC);
    }

    public void ifNotNull() {
        if (beanC == null) {
            System.out.println("Now BeanC is null.");
        }else {
            System.out.println("Now BeanC not null.");
        }
    }

}
