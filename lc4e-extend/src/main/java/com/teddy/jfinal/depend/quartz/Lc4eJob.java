package com.teddy.jfinal.depend.quartz;

import org.quartz.Job;

/**
 * Created by teddy on 2015/8/12.
 */
public class Lc4eJob {

    private Job job;
    private String name;
    private String corn;

    private String group;

    private String className;

    public Lc4eJob(Job job, String name, String corn, String className, String group) {
        this.job = job;
        this.name = name;
        this.corn = corn;
        this.className = className;
        this.group = group;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
