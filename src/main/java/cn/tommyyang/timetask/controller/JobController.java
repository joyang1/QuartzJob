package cn.tommyyang.timetask.controller;

import cn.tommyyang.timetask.Utils.QuartzManager;
import cn.tommyyang.timetask.service.jobs.Job1Impl;
import cn.tommyyang.timetask.service.jobs.Job2Impl;
import cn.tommyyang.timetask.service.jobs.Job3Impl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TommyYang on 2018/2/1.
 */
@Controller
@RequestMapping("job")
public class JobController extends BaseController{

    @RequestMapping("/runjobs.do")
    @ResponseBody
    public String runJob(HttpServletRequest request, HttpServletResponse response){
        String job1 = "job1";
        String job2 = "job2";
        String job3 = "job3";
        String jobGroup = "jobGroup";
        String trigger1 = "trigger1";
        String trigger2 = "trigger2";
        String trigger3 = "trigger3";
        String triggerGroup = "triggerGroup";
        String cron1 = "0 17/1 16 * * ?";
        String cron2 = "0 17/2 16 * * ?";
        String cron3 = "0 17/3 16 * * ?";
        QuartzManager.addjob(job1, jobGroup, trigger1, triggerGroup, Job1Impl.class,cron1);
        QuartzManager.addjob(job2, jobGroup, trigger2, triggerGroup, Job2Impl.class,cron2);
        QuartzManager.addjob(job3, jobGroup, trigger3, triggerGroup, Job3Impl.class,cron3);
        return "success";
    }

    @RequestMapping("/deljobs.do")
    @ResponseBody
    public String delJob(){
        String job3 = "job3";
        String jobGroup = "jobGroup";
        String trigger3 = "trigger3";
        String triggerGroup = "triggerGroup";
        QuartzManager.removeJob(job3, jobGroup, trigger3, triggerGroup);
        return "success";
    }

    @RequestMapping("/shutdownjobs.do")
    @ResponseBody
    public String shutdownJobs(){
        QuartzManager.shutdownJobs();
        return "success";
    }


}
