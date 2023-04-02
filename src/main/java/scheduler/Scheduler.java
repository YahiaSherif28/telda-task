package scheduler;

import java.time.Clock;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;


public class Scheduler implements Runnable {

    private final TreeMap<JobContainer, Thread> jobs;
    private final HashMap<String, JobContainer> getJobByIdentifier;

    private final Clock clock;

    public Scheduler(Clock clock) {
        this.jobs = new TreeMap<>();
        this.getJobByIdentifier = new HashMap<>();
        this.clock = clock;
    }


    public TreeMap<JobContainer, Thread> getJobs() {
        return jobs;
    }

    public HashMap<String, JobContainer> getGetJobByIdentifier() {
        return getJobByIdentifier;
    }

    public Clock getClock() {
        return clock;
    }

    /**
     * Stops a job from being scheduled temporarily
     */
    public void startJob(JobContainer jobContainer) {
        Thread thread = new Thread(jobContainer);
        jobs.put(jobContainer, thread);
        getJobByIdentifier.put(jobContainer.getIdentifier(), jobContainer);
        thread.start();
    }

    /**
     * Stops a job from being scheduled temporarily
     */
    public void stopJob(String identifier) {
        JobContainer jobContainer = getJobByIdentifier.get(identifier);
        jobs.remove(jobContainer);
    }

    /**
     * Resumes a job which was stopped
     */
    public void resumeJob(String identifier) {
        JobContainer jobContainer = getJobByIdentifier.get(identifier);
        Thread thread = new Thread(jobContainer);
        jobContainer.setDueTime();
        jobs.put(jobContainer, thread);
        thread.start();
    }

    /**
     * Removes a job permanently from the scheduler
     */
    public void removeJob(String identifier) {
        stopJob(identifier);
        getJobByIdentifier.remove(identifier);
    }

    /**
     * Schedules the job for its next run
     */
    private void restartJob(JobContainer jobContainer) {
        jobs.remove(jobContainer);
        Thread thread = new Thread(jobContainer);
        jobContainer.setDueTime();
        jobs.put(jobContainer, thread);
        thread.start();
    }


    @Override
    public void run() {
        while (true) {
            if (!jobs.isEmpty() && jobs.firstKey().isDue()) {
                restartJob(jobs.firstKey());
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }
}
