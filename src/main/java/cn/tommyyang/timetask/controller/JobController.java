package cn.tommyyang.timetask.controller;

import cn.tommyyang.timetask.Utils.QuartzManager;
import cn.tommyyang.timetask.model.Job;
import cn.tommyyang.timetask.service.jobs.Job1Impl;
import cn.tommyyang.timetask.service.jobs.Job2Impl;
import cn.tommyyang.timetask.service.jobs.Job3Impl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TommyYang on 2018/2/1.
 */
@Controller
@RequestMapping("job")
public class JobController extends BaseController {

    @RequestMapping(value = "/addjob.do", method = RequestMethod.GET)
    @ResponseBody
    public String addJob(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "cron") String cron) {
        String job = name;
        String jobGroup = "jobGroup";
        String trigger = "trigger" + job;
        String triggerGroup = "triggerGroup";
        Class clazz = Job.getClassByName(name);
        QuartzManager.addjob(job, jobGroup, trigger, triggerGroup, clazz, cron);
        return job + " add success";
    }

    @RequestMapping(value = "modifyjob.do", method = RequestMethod.GET)
    @ResponseBody
    public String modifyJob(@RequestParam(value = "name") String name,
                            @RequestParam(value = "cron") String cron){
        String job = name;
        String trigger = "trigger" + job;
        String triggerGroup = "triggerGroup";
        QuartzManager.modifyJobTime(trigger, triggerGroup, cron);
        return String.format("modify job:%s success", job);
    }

    @RequestMapping("/deljob.do")
    @ResponseBody
    public String delJob(@RequestParam(value = "name")String name) {
        String job = name;
        String jobGroup = "jobGroup";
        String trigger = "trigger"+job;
        String triggerGroup = "triggerGroup";
        QuartzManager.removeJob(job, jobGroup, trigger, triggerGroup);
        return job + " delete success";
    }

    @RequestMapping("/shutdownjobs.do")
    @ResponseBody
    public String shutdownJobs() {
        QuartzManager.shutdownJobs();
        return "shutdown jobs success";
    }

    @RequestMapping("/runjobimme.do")
    @ResponseBody
    public String runjobImmediately(@RequestParam(value = "name")String name) {
        String job = name;
        String jobGroup = "jobGroup";
        String data = "testData";
        QuartzManager.startJobImmediately(job, jobGroup, data, new Date(System.currentTimeMillis()));
        return "success";
    }


}
