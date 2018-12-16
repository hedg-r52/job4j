package parser;

import org.quartz.*;
import org.quartz.impl.*;

public class JobSheduler {
    public static void main(String[] args) {
        String exp = "0 46 15 * * ?";
        try {
            JobDetail job = JobBuilder.newJob(ParserJob.class).withIdentity("parserjob").build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(
                        CronScheduleBuilder.cronSchedule(exp))
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
