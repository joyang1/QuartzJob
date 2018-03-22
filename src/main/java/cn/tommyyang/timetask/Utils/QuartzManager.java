package cn.tommyyang.timetask.Utils;

import cn.tommyyang.timetask.service.jobs.Job4Impl;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by TommyYang on 2018/2/1.
 */
public class QuartzManager {

    private final static Logger logger = LoggerFactory.getLogger(QuartzManager.class);
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * 功能： 添加一个定时任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass 任务的类类型 eg:TimedMassJob.class
     * @param cron 时间设置 表达式，参考quartz说明文档
     * @param objects 可变参数需要进行传参的值
     */
    public static void addjob(String jobName, String jobGroupName, String triggerName,String triggerGroupName, Class jobClass, String cron, Object... objects){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            //传递参数
            if(objects != null){
                Integer i = 0;
                for (Object obj: objects) {
                    jobDetail.getJobDataMap().put("data"+i, obj);
                    i++;
                }
            }
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 触发器
            CronTrigger cronTrigger = (CronTrigger)triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            if(!scheduler.isShutdown()){
                scheduler.start();
            }
        } catch (SchedulerException e) {
            logger.error("addjob error:\n",e);
        }
    }

    /**
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param object 参数
     * @param time job运行时间
     *
     */

    public static void startJobImmediately(String jobName, String jobGroupName, Object object, Date time){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder
                    .newJob(Job4Impl.class)
                    .withIdentity(jobName, jobGroupName).build();
            jobDetail.getJobDataMap().put("list", object);
            SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                    .withIdentity(jobName, jobGroupName).startAt(time).build();
            scheduler.scheduleJob(jobDetail, simpleTrigger);
            if(!scheduler.isShutdown()){
                scheduler.start();
            }
        } catch (SchedulerException e) {
            logger.error("startJobImmediately error:\n",e);
        }



    }

    /**
     * 功能：修改一个任务的触发时间
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param cron 时间设置，参考quartz说明文档
     */
    public static void modifyJobTime(String triggerName,String triggerGroupName, String cron){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            if(trigger == null){
                return;
            }
            String oldTime = trigger.getCronExpression();
            if(!oldTime.equalsIgnoreCase(cron)){
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                trigger = (CronTrigger) triggerBuilder.build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            logger.error("modifyJobTime error:\n",e);
        }
    }

    /**
     * 功能: 移除一个任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void removeJob(String jobName, String jobGroupName,String triggerName, String triggerGroupName){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName,jobGroupName));// 删除任务
        } catch (SchedulerException e) {
            logger.error("removeJob error:\n",e);
        }
    }

    public static void startJobs(){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("startJobs error:\n",e);
        }
    }

    public static void shutdownJobs(){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error("shutdownJobs error:\n",e);
        }
    }


}
