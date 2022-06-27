package monex;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MonExActivity {
    @ActivityMethod
    String getValue(MonExParams params);
}
