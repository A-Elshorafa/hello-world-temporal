package monex;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface MonExWorkflow {
    @WorkflowMethod(name = SharedVertxInstance.MON_EX_WORKFLOW_TYPE)
    String getExchangeValue(MonExParams params);
}
