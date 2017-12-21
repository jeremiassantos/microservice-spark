package br.com.spark.config;

import br.com.spark.annotation.SparkRegister;
import br.com.spark.exception.MessageException;
import br.com.spark.exception.MessageInvalidException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

/**
 * Created on 21/12/2017.
 */
@SparkRegister
@Component
public class ConfigurationSpark implements Spark {

    @Override
    public void register() {
        configureHeaders();
        configureException();
    }

    private void portConfigure() {
        port(8001);
    }

    private void configureHeaders() {
        before((Request request, Response response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Credentials", "true");
            response.header("Access-Control-Max-Age", "3600");
            response.header("Access-Control-Allow-Headers", "api, versao, Content-Type, Access-Control-Allow-Headers, Access-Control-Request-Method, Authorization, X-Requested-With, Accept-Encoding");
            response.header("Content-Type", "application/json");
        });

        after((request, response) -> {
            response.header("Content-Encoding", "gzip");
        });
    }

    private void configureException() {
        exception(MessageException.class, (e, req, resp) -> {
            resp.body("MessageException " + e.getMessage());
            resp.status(400);
            resp.type("text/plain");
        });

        exception(MessageInvalidException.class, (e, req, resp) -> {
            resp.body("MessageInvalidException " + e.getMessage());
            resp.status(400);
            resp.type("text/plain");
        });
    }

}
