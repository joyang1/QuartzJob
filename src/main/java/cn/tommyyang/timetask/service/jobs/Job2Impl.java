package cn.tommyyang.timetask.service.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by TommyYang on 2018/2/1.
 */
public class Job2Impl implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //do something you want to do
        System.out.println("job 2 start!!!");
    }
}
