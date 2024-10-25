package org.yassine.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextProvider {
    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static ApplicationContext getContext() {
        return context;
    }
}

