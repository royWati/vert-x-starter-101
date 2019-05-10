import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class FirstVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        vertx.createHttpServer()
                .requestHandler(httpServerRequest -> {
                    httpServerRequest.response()
                            .end("verticle server deployed");
                })
                .listen(config().getInteger("server.port",8810),  // Reads from a configuration file
                        httpServerAsyncResult -> {
                    if(httpServerAsyncResult.succeeded())
                        future.complete();
                    else
                        future.fail(httpServerAsyncResult.cause());
                });
    }
}
