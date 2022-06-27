package helloworldapp;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class HelloWorldWorkflowImpl implements HelloWorldWorkflow{
    ActivityOptions options = ActivityOptions.newBuilder()
        .setScheduleToCloseTimeout(Duration.ofSeconds(2))
        .build();

    private final Format format = Workflow.newActivityStub(Format.class, options);
    @Override
    public String getGreeting(String name) {
        return format.composeGreeting(name);
    }
}
