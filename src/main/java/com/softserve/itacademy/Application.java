package com.softserve.itacademy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        for(String s : annotationConfigContext.getBeanDefinitionNames()){
            System.out.println(s);
        }

    }

}
