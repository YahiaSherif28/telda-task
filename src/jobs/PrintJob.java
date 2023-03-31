package jobs;

import scheduler.Job;

/**
 * Sample job to test
 * Jobs can be added at runtime
 */
public class PrintJob implements Job {
    @Override
    public void execute() {
        System.out.println("This is the printing job");
        System.out.flush();
    }
}
