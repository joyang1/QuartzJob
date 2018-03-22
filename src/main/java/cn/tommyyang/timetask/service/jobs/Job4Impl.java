package cn.tommyyang.timetask.service.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by TommyYang on 2018/3/22.
 */
public class Job4Impl implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        List<String> strs = (List<String>) dataMap.get("list");
        for (String str:strs) {
            System.out.println(str);
        }
    }
}
