package scheduler;

import java.time.Duration;

public class Util {
    public static long parseTime(String s) {
        return Duration.parse("PT" + s.toUpperCase()).toMillis();
    }
}
