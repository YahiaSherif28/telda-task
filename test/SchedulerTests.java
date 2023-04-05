import jobs.PrintJob;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;
import org.mockito.MockedStatic;
import scheduler.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;

public class SchedulerTests {

    Controller controller;
    Scheduler scheduler;
    static final long START_TIME = 1_000_000_000;
    static final long TEN_SECONDS = Duration.ofSeconds(10).toMillis();

    @BeforeEach
    public void setUp() {
        controller = new Controller(Clock.fixed(Instant.ofEpochMilli(START_TIME), ZoneId.of("Africa/Cairo")));
        scheduler = new Scheduler(Clock.fixed(Instant.ofEpochMilli(START_TIME), ZoneId.of("Africa/Cairo")));
    }

    @Test
    public void startJobTest() throws Exception {

        JobContainer jobContainer = new JobContainer(new PrintJob(), "testJob", TEN_SECONDS, TEN_SECONDS, scheduler.getClock());

        scheduler.startJob(jobContainer);

        Assertions.assertIterableEquals(scheduler.getJobs().keySet(), new ArrayList<>(List.of(jobContainer)));
        Assertions.assertEquals(scheduler.getGetJobByIdentifier(), Map.of("testJob", jobContainer));
    }

    @Test
    public void stopJobTest() throws Exception {

        JobContainer jobContainer = new JobContainer(new PrintJob(), "testJob", TEN_SECONDS, TEN_SECONDS, scheduler.getClock());

        scheduler.startJob(jobContainer);

        scheduler.stopJob("testJob");

        Assertions.assertIterableEquals(scheduler.getJobs().keySet(), new ArrayList<>());
        Assertions.assertEquals(scheduler.getGetJobByIdentifier(), Map.of("testJob", jobContainer));
    }

    @Test
    public void resumeJobTest() throws Exception {

        JobContainer jobContainer = new JobContainer(new PrintJob(), "testJob", TEN_SECONDS, TEN_SECONDS, scheduler.getClock());

        scheduler.startJob(jobContainer);


        scheduler.stopJob("testJob");

        scheduler.resumeJob("testJob");
        Assertions.assertIterableEquals(scheduler.getJobs().keySet(), new ArrayList<>(List.of(jobContainer)));
        Assertions.assertEquals(scheduler.getGetJobByIdentifier(), Map.of("testJob", jobContainer));
    }

    @Test
    public void removeJobTest() throws Exception {

        JobContainer jobContainer = new JobContainer(new PrintJob(), "testJob", TEN_SECONDS, TEN_SECONDS, scheduler.getClock());

        scheduler.startJob(jobContainer);


        scheduler.removeJob("testJob");

        Assertions.assertIterableEquals(scheduler.getJobs().keySet(), new ArrayList<>());
        Assertions.assertEquals(scheduler.getGetJobByIdentifier(), Map.of());
    }

    @Test
    public void restartJobTest() throws Exception {

        JobContainer jobContainer = new JobContainer(new PrintJob(), "testJob", TEN_SECONDS, TEN_SECONDS, scheduler.getClock());

        scheduler.startJob(jobContainer);

        scheduler.setClock(Clock.fixed(Instant.ofEpochMilli(START_TIME + TEN_SECONDS), ZoneId.of("Africa/Cairo")));

        scheduler.restartJob(jobContainer);

        Assertions.assertEquals(jobContainer.getDueTime(), START_TIME + 2 * TEN_SECONDS);
        Assertions.assertIterableEquals(scheduler.getJobs().keySet(), new ArrayList<>(List.of(jobContainer)));
        Assertions.assertEquals(scheduler.getGetJobByIdentifier(), Map.of("testJob", jobContainer));
    }


}
