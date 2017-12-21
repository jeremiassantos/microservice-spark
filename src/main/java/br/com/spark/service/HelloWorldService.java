package br.com.spark.service;

import org.springframework.stereotype.Service;

/**
 * Created by jeremiassantos on 21/12/2017.
 */
@Service
public class HelloWorldService {

    public String hello() {
        System.out.print("Hello");
        return "Hello";
    }
}
