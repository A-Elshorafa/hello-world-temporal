package monex;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MonExWorkflowImpl implements MonExWorkflow {
    ActivityOptions options = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofSeconds(120))
            .build();

    private final MonExActivity monExchange = Workflow.newActivityStub(MonExActivity.class, options);
    @Override
    public String getExchangeValue(MonExParams params) {
        return monExchange.getValue(params);
    }
}
