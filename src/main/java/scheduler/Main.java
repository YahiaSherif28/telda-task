package scheduler;

import java.time.Clock;

public class Main {
    public static void main(String[] args) {
//        System.out.println(jobs.jobs.PrintJob.class.getName());
        Controller controller = new Controller(Clock.systemDefaultZone());
        controller.start();
    }
}