package monex;

import com.google.gson.Gson;
import helloworldapp.Shared;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.vertx.core.AbstractVerticle;

import java.util.Date;

public class ResponseVerticle extends AbstractVerticle {
    public void start() {
        Gson g = new Gson();

        vertx.eventBus().consumer("monex.begin.workflow", msg -> {
            String uniqueMSecond = String.valueOf(new Date().getTime());
            System.out.println("uniqueId: "+ uniqueMSecond);
            WorkflowOptions options = WorkflowOptions.newBuilder()
//                    .setWorkflowId(Shared.CURRENCY_EXCHANGE+ '_' +uniqueMSecond)
                    .setTaskQueue(Shared.CURRENCY_EXCHANGE)
                    .build();

            WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

            WorkflowClient client = WorkflowClient.newInstance(service);

            WorkflowStub workflowStub = client.newUntypedWorkflowStub(SharedVertxInstance.MON_EX_WORKFLOW_TYPE, options);

            MonExWorkflow workflow = client.newWorkflowStub(MonExWorkflow.class, options);
            MonExParams responseObj = g.fromJson(msg.body().toString(), MonExParams.class);
//            String workflowExec = workflow.getExchangeValue(responseObj);
//            System.out.println("result: " + workflowExec);
            WorkflowClient.start(workflow::getExchangeValue, responseObj);
//            WorkflowStub workflowStub = WorkflowStub.fromTyped(workflow);
            try {
                workflowStub
                    .getResultAsync(String.class)
                    .thenApply(result -> {
//                        msg.reply("result: " + result);
                        System.out.println("result: " + result);
                        return result;
                    });
            } catch (Exception ex) {
//                msg.reply("end");
                System.out.println("end");
            }
            System.out.println("done");
        });
    }
}

//    WorkflowStub workflowStub = client.newUntypedWorkflowStub(SharedVertxInstance.MON_EX_WORKFLOW_TYPE, options);
//
//    MonExWorkflow workflow = workflowStub.getResult(MonExWorkflow.class);

//    Synchronously execute the Workflow and wait for the response.
//    String exchange = WorkflowClient.start(MonExWorkflow::getExchangeValue, responseObj);
//    System.out.println(exchange);