package br.com.spark.api;

import br.com.spark.annotation.SparkRegister;
import br.com.spark.config.Spark;
import br.com.spark.dto.MyMessage;
import br.com.spark.exception.MessageException;
import br.com.spark.exception.MessageInvalidException;
import br.com.spark.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.spark.transform.JsonTransformer.json;
import static br.com.spark.transform.JsonTransformer.toObject;
import static spark.Spark.*;

@SparkRegister
@Component
public class ApiSpark implements Spark {

    @Autowired
    private HelloWorldService helloWorldService;

    @Override
    public void register() {

        get("/hello", (req, res) -> "Hello World");

        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name");
        });

        get("/helloTeste", "application/json", (request, response) ->
                        new MyMessage("Hello World")
                , json());

        post("/save", (req, res) -> {

            res.type("application/json");

            MyMessage myMessage = toObject(req.body(), MyMessage.class);

            if(myMessage == null) {
                throw new MessageException("Message can not nul!");
            }

            if(myMessage.getMessage() == null) {
                throw new MessageInvalidException("Message is invalid!");
            }

            return myMessage;
        }, json());

        get("/helloSpring", (request, response) -> {
            response.type("text/plain");

            return helloWorldService.hello();
        } );
    }

}