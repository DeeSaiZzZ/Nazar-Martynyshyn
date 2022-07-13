package com.epam.spring.homework2.configs;

import com.epam.spring.homework2.beans.BeanB;
import com.epam.spring.homework2.beans.BeanC;
import com.epam.spring.homework2.beans.BeanD;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@Import(SecondConfig.class)
public class FirstConfig {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    @DependsOn("getBeanD")
    public BeanB getBeanB(@Value("${beanB.name}") final String name, @Value("${beanB.value}") final int value) {
        return new BeanB(name, value);
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    @DependsOn("getBeanB")
    public BeanC getBeanC(@Value("${beanC.name}") final String name, @Value("${beanC.value}") final int value) {
        return new BeanC(name, value);
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanD getBeanD(@Value("${beanD.name}") final String name, @Value("${beanD.value}") final int value) {
        return new BeanD(name, value);
    }
}
