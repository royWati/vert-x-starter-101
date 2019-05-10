import io.netty.handler.codec.http.HttpResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class FirstVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        // Create a router object.
        Router router = Router.router(vertx);

        router.route("/produce-string").handler(event -> {
           // CONFIGURING THE INTENDED RESPONSE
            HttpServerResponse response = event.response();
            response.putHeader("content-type","text/html");
            response.end("this is a string produced by a vert.x application");
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
}
