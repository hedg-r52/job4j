package parser;

import org.quartz.*;
import org.quartz.impl.*;

public class JobScheduler {

    private final Config config;

    public static void main(String[] args) {
        new JobScheduler().init();
    }

    public JobScheduler() {
        config = new Config();
        config.load("app.properties");
    }

    private void init() {
        try {
            JobDetail job = JobBuilder.newJob(ParserJob.class).withIdentity("parserjob").build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(config.getValue("cron.time")))
                    .build();
            SchedulerFactory schFactory = new StdSchedulerFactory();
            Scheduler scheduler = schFactory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
