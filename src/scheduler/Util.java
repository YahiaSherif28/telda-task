package scheduler;

import java.time.Duration;

public class Util {
    static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    static long parseTime(String s) {
        return Duration.parse("PT" + s.toUpperCase()).toMillis();
    }
}
