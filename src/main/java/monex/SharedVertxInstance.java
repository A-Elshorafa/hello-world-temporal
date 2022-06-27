package monex;

import io.vertx.core.Vertx;

public class SharedVertxInstance {
    public static Vertx vertx = Vertx.vertx();
    public final static String MON_EX_WORKFLOW_TYPE = "MonExWorkflowType";
}
