package scheduler;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Controller {

    Scheduler scheduler;
    Scanner scanner;

    public Controller() {
        scheduler = new Scheduler();
        scanner = new Scanner(System.in);
    }

    public void startJob(Job job, String identifier, long expectedTime, long frequency) {
        JobContainer jobContainer = new JobContainer(job, identifier, expectedTime, frequency, Util.getCurrentTime() + frequency);
        scheduler.startJob(jobContainer);
    }

    private void startScheduler() {
        new Thread(scheduler).start();
    }

    // could use command design pattern here to allow for more commands

    /**
     * Starts a job
     */
    public void startCommand() throws Exception {
        String job = scanner.next();
        String identifier = scanner.next();
        String expectedTime = scanner.next();
        String frequency = scanner.next();

        Class<?> jobClass = Class.forName(job);
        Job jobInstance = (Job) jobClass.getDeclaredConstructor().newInstance();
        startJob(jobInstance, identifier, Util.parseTime(expectedTime), Util.parseTime(frequency));
    }

    /**
     * For debugging
     */
    public void debugCommand() {
        System.err.println(scheduler.getJobs());
        if (!scheduler.getJobs().isEmpty()) {
            System.err.println(scheduler.getJobs().firstKey().isDue());
        }
    }

    /**
     * Stops a job from being scheduled temporarily
     */
    public void stopCommand() {
        String identifier = scanner.next();
        scheduler.stopJob(identifier);
    }

    /**
     * Resumes a job which was stopped
     */
    public void resumeCommand() {
        String identifier = scanner.next();
        scheduler.resumeJob(identifier);
    }

    /**
     * Removes a job permanently from the scheduler
     */
    public void removeCommand() {
        String identifier = scanner.next();
        scheduler.removeJob(identifier);
    }

    public void start() {
        startScheduler();
        while (true) {
            System.out.println("A");
            String cmd = scanner.next();
            try {
                switch (cmd) {
                    case "start":
                        startCommand();
                        break;
                    case "stop":
                        stopCommand();
                        break;
                    case "resume":
                        resumeCommand();
                        break;
                    case "remove":
                        removeCommand();
                        break;
                    case "debug":
                        debugCommand();
                        break;
                    case "exit":
                        return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
