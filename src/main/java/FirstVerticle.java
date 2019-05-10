import io.netty.handler.codec.http.HttpResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.List;
import java.util.Map;

public class FirstVerticle extends AbstractVerticle {
    List data = new LanguageService().initData();
    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        // Create a router object.


        Router router = Router.router(vertx);

        //enabling reading of request bodies globally
        router.route("/vertx*").handler(BodyHandler.create());

        router.route("/vertx/produce-string").handler(event -> {
           // CONFIGURING THE INTENDED RESPONSE
            HttpServerResponse response = event.response();
            response.putHeader("content-type","text/html");
            response.end("this is a string produced by a vert.x application");
        });

        //The first line enables the reading of the request body for all routes under


        // routing to a get request mapping
        router.get("/vertx/languages").handler(this::getAllLanguages);
        router.post("/vertx/languages").handler(this::addLanguage);

        // creating a json object
        router.route("/produce-json-example").handler(event -> {
            JsonObject object = new JsonObject();
            object.put("library","vert-x");
            object.put("language","springboot");

            HttpServerResponse response = event.response();
            response.putHeader("content-type","application/json");
            response.end(object.toString());
        });

        vertx.createHttpServer()
                .requestHandler(router::accept)  // routing our request to our intended response

                .listen(config().getInteger("server.port",8801),  // Reads from a configuration file
                        httpServerAsyncResult -> {
                    if(httpServerAsyncResult.succeeded())
                        future.complete();
                    else
                        future.fail(httpServerAsyncResult.cause());
                });
    }

    // using a custom method to a route handler

    public void getAllLanguages(RoutingContext routingContext){
        routingContext.response()
                .putHeader("content-type","application/json")
                .end(Json.encodePrettily(data));
    }

    //reading a request body
    public void addLanguage(RoutingContext routingContext){

        final Languages languages = Json.decodeValue(routingContext.getBodyAsString(),Languages.class);
        data.add(languages);

        routingContext.response()
                .putHeader("content-type","application/json")
                .end(Json.encodePrettily(data));
    }
}
