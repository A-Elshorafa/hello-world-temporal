package monex;

import io.vertx.core.Vertx;

public class AppIntializer {
    public static void main(String[] args) {
        Vertx vertx = SharedVertxInstance.vertx;
        AppServer serverVerticle = new AppServer();
        MonExWorker workerVerticle = new MonExWorker();
        ResponseVerticle responseVerticle = new ResponseVerticle();
        vertx.deployVerticle(serverVerticle);
        vertx.deployVerticle(workerVerticle);
        vertx.deployVerticle(responseVerticle);
    }
}
