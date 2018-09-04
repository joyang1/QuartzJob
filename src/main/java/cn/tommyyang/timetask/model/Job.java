package cn.tommyyang.timetask.model;

import cn.tommyyang.timetask.service.jobs.Job1Impl;

/**
 * Created by TommyYang on 2018/9/4.
 */
public enum Job {

    Job1("job1", Job1Impl.class),
    Job2("job2", Job1Impl.class),
    Job3("job3", Job1Impl.class),
    Job4("job4", Job1Impl.class);

    private String name;
    private Class clazz;

    Job(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class getClazz() {
        return clazz;
    }

    public static Class getClassByName(String name){
        for(Job job : Job.values()){
            if(job.getName().equals(name)){
                return job.getClazz();
            }
        }
        return null;
    }
}
