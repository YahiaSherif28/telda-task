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
        Job concreteJob = new PrintJob();
        JobContainer jobContainer = new JobContainer(concreteJob, "testJob", TEN_SECONDS, TEN_SECONDS, scheduler.getClock());
        scheduler.startJob(jobContainer);
        Assertions.assertTrue(scheduler.getJobs().containsKey(jobContainer));
        Assertions.assertTrue(scheduler.getGetJobByIdentifier().containsKey("testJob"));
    }
}
