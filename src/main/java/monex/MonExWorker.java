package monex;

import helloworldapp.Shared;
import io.temporal.worker.Worker;
import io.vertx.core.AbstractVerticle;
import io.temporal.worker.WorkerFactory;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class MonExWorker extends AbstractVerticle {
    public void start() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(Shared.CURRENCY_EXCHANGE);
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful, so you need to supply a type to create instances.
        worker.registerWorkflowImplementationTypes(MonExWorkflowImpl.class);
        // Activities are stateless and thread safe, so a shared instance is used.
        worker.registerActivitiesImplementations(new MonExActivityImpl());
        // Start polling the Task Queue.
        factory.start();
    }
}
