package monex;

import helloworldapp.HelloWorldWorkflow;
import helloworldapp.Shared;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class IntiMonEx {
    public static void main(String[] args) {
        // This gRPC stubs wrapper talks to the local docker instance of the Temporal service.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.CURRENCY_EXCHANGE)
                .build();
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        MonExWorkflow workflow = client.newWorkflowStub(MonExWorkflow.class, options);
        Vertx vertx = SharedVertxInstance.vertx;
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("monex.run.workflow", message -> {
            // Synchronously execute the Workflow and wait for the response.
            MonExParams params = new MonExParams();
            params.to = "USD";
            params.from = "EGP";
            params.amount = "10";
            String exchange = workflow.getExchangeValue(params);
            System.out.println(exchange);
        });
//        System.exit(0);
    }
}