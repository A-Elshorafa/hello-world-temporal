package helloworldapp;

import io.temporal.activity.ActivityMethod;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface Format {

    @ActivityMethod
    String composeGreeting(String name);
}
