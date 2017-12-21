package br.com.spark.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextSupport implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
      applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
      return applicationContext;
    }

    public static <T> T getBean(Class<T> bean) {
        return getApplicationContext().getBean(bean);
    }
}