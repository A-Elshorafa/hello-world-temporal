package monex;

import com.google.gson.Gson;
import io.vertx.ext.web.Router;
import io.vertx.core.http.HttpServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.handler.BodyHandler;

public class AppServer extends AbstractVerticle {
    public void start() {
        HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);

        EventBus eventBus = vertx.eventBus();
        router
                .post("/sayHello")
                .handler(BodyHandler.create())
                .handler(routingContext -> {
                    MonExParams params = new MonExParams();
                    params.to = routingContext.request().params().get("to");
                    params.from = routingContext.request().params().get("from");
                    params.amount = routingContext.request().params().get("amount");
                    routingContext.request().response().end("In Progress...");
                    try {
                        eventBus.request("monex.begin.workflow", new Gson().toJson(params), reply -> {
//                            routingContext.request().response().end(reply.result().body().toString());
                        });
                    } catch (Exception ex) {
                        routingContext.request().response().end("END");
                    }
                });
        httpServer.requestHandler(router).listen(8094);
    }
}
