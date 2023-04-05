package scheduler;


import java.time.Clock;

public class JobContainer implements Comparable<JobContainer>, Runnable {
    private final Job job;
    private final String identifier;
    private final long expectedTime, frequency;
    private long dueTime;


    public JobContainer(Job job, String identifier, long expectedTime, long frequency, Clock clock) {
        this.job = job;
        this.identifier = identifier;
        this.expectedTime = expectedTime;
        this.frequency = frequency;
        this.dueTime = getCurrentTime(clock) + frequency;
    }

    private long getCurrentTime(Clock clock) {
        return clock.millis();
    }


    public void setDueTime(Clock clock) {
        dueTime = getCurrentTime(clock) + frequency;
    }

    public long getDueTime() {
        return dueTime;
    }

    public boolean isDue(Clock clock) {
        return dueTime <= getCurrentTime(clock);
    }

    public String getIdentifier() {
        return identifier;
    }

    public long priorityScore() {
        return dueTime;
    }

    @Override
    public int compareTo(JobContainer o) {
        double prio = priorityScore();
        double otherPrio = o.priorityScore();
        return prio != otherPrio ? Double.compare(prio, otherPrio) : identifier.compareTo(o.identifier);
    }

    @Override
    public String toString() {
        return "scheduler.JobContainer{" +
                "job=" + job +
                ", identifier='" + identifier + '\'' +
                ", expectedTime=" + expectedTime +
                ", frequency=" + frequency +
                ", dueTime=" + dueTime +
                '}';
    }

    @Override
    public final void run() {
        System.out.printf("scheduler.Job %s is starting\n", identifier);
        job.execute();
        System.out.printf("scheduler.Job %s has finished\n", identifier);
    }
}
