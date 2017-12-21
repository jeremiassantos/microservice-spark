# Microservice in spark

A Spark DSL in idiomatic.

Dependency:
-----------
Maven:
```xml
<dependency>
    <groupId>com.sparkjava</groupId>
    <artifactId>spark-core</artifactId>
    <version>2.3</version>
</dependency>
```

Documentation
-------------

Routes
------

```kotlin
// Static API
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
```
Initialization
------------------
```java
// Static API
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
```
