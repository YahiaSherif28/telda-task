package jobs;

import scheduler.Job;

public class TeldaJob implements Job {
    public void execute() {
        System.out.println("We are Telda");
        System.out.flush();
    }
}
