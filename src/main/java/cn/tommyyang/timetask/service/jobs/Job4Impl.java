package cn.tommyyang.timetask.service.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by TommyYang on 2018/3/22.
 */
public class Job4Impl implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String data = dataMap.get("data").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = format.format(new Date());
        System.out.println("job4 start at " + time);
        System.out.println("job4 transfer data is " + data);
    }
}
