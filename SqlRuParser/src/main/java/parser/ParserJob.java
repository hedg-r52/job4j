package parser;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.sql.SQLException;

public class ParserJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Cron executing...");
        try {
            new ParserSqlRu().execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
