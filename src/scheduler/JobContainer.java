package scheduler;

public class JobContainer implements Comparable<JobContainer>, Runnable {
    private final Job job;
    private final String identifier;
    private final long expectedTime, frequency;
    private long dueTime;

    public JobContainer(Job job, String identifier, long expectedTime, long frequency) {
        this.job = job;
        this.identifier = identifier;
        this.expectedTime = expectedTime;
        this.frequency = frequency;
    }

    public JobContainer(Job job, String identifier, long expectedTime, long frequency, long dueTime) {
        this.job = job;
        this.identifier = identifier;
        this.expectedTime = expectedTime;
        this.frequency = frequency;
        this.dueTime = dueTime;
    }

    public void setDueTime() {
        dueTime = Util.getCurrentTime() + frequency;
    }

    public boolean isDue() {
        return dueTime <= Util.getCurrentTime();
    }

    public String getIdentifier() {
        return identifier;
    }

    public double priorityScore() {
        return dueTime;
    }

    @Override
    public int compareTo(JobContainer o) {
        double prio = priorityScore();
        double otherPrio = o.priorityScore();
        return prio == otherPrio ? Double.compare(prio, otherPrio) : identifier.compareTo(o.identifier);
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
