package br.com.spark.config;

import br.com.spark.annotation.SparkRegister;
import org.reflections.Reflections;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ApplicationConfiguration {

    private static final Logger LOGGER = Logger.getLogger(ApplicationConfiguration.class.getName());

    static {

        configureSpring();

        Reflections reflections = new Reflections("br.com.spark");

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(SparkRegister.class);

        annotated = annotated.stream().sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toSet());

        for (Class<?> class1 : annotated) {
            @SuppressWarnings("rawtypes")
            Class clz = class1;

            try {
                Spark spark = (Spark) clz.newInstance();

                spark = ApplicationContextSupport.getBean(spark.getClass());

                LOGGER.info("Instance " + spark.getClass().getSimpleName());

                spark.register();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void configureSpring() {
        new ClassPathXmlApplicationContext("classpath:application-context.xml");
    }
}