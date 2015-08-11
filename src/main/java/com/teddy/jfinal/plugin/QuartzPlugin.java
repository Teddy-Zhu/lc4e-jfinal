package com.teddy.jfinal.plugin;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.lc4e.core.database.mapping.T_Sys_Job;
import com.teddy.lc4e.core.database.model.Sys_Job;
import com.teddy.lc4e.core.web.service.JobService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

public class QuartzPlugin implements IPlugin {
    private static final String JOB = "job";
    private final Logger logger = Logger.getLogger(getClass());
    private Map<Job, String> jobs = new HashMap<>();
    private SchedulerFactory sf;
    private Scheduler scheduler;
    private String jobConfig;
    private String confConfig;
    private Map<String, String> jobProp;

    public QuartzPlugin(String jobConfig, String confConfig) {
        this.jobConfig = jobConfig;
        this.confConfig = confConfig;
    }

    public QuartzPlugin(String jobConfig) {
        this.jobConfig = jobConfig;
    }

    public QuartzPlugin() {
    }

    public QuartzPlugin add(String jobCronExp, Job job) {
        jobs.put(job, jobCronExp);
        return this;
    }

    @Override
    public boolean start() {


        startJobs();
        return true;
    }

    private void loadJobs() {
        List<Sys_Job> allJobs = JobService.service.getAllJobs();
        allJobs.forEach(sys_job -> {
            Class<Job> job = ReflectTool.on(sys_job.getStr(T_Sys_Job.className)).get();
            try {
                jobs.put(job.newInstance(), sys_job.getStr(T_Sys_Job.cron));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void startJobs() {
        try {
            if (StrKit.notBlank(confConfig)) {
                sf = new StdSchedulerFactory(confConfig);
            } else {
                sf = new StdSchedulerFactory();
            }
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<Job, String>> set = jobs.entrySet();
        for (Map.Entry<Job, String> entry : set) {
            Job job = entry.getKey();
            String jobClassName = job.getClass().getName();
            String jobCronExp = entry.getValue();
            JobBuilder jobBuilder;
            TriggerBuilder triggerBuilder;

            jobBuilder = ReflectTool.on("org.quartz.JobBuilder").call("newJob", job.getClass()).call("withIdentity", jobClassName, jobClassName)
                    .call("build").get();
            Object temp = ReflectTool.on("org.quartz.TriggerBuilder").call("newTrigger").get();
            temp = ReflectTool.on(temp).call("withIdentity", jobClassName, jobClassName).get();
            temp = ReflectTool.on(temp).call("withSchedule",
                    ReflectTool.on("org.quartz.CronScheduleBuilder").call("cronSchedule", jobCronExp).get())
                    .get();
            triggerBuilder = ReflectTool.on(temp).call("build").get();

            Date ft = ReflectTool.on(scheduler).call("scheduleJob", jobBuilder, triggerBuilder).get();
            logger.debug(ReflectTool.on(jobBuilder).call("getKey") + " has been scheduled to run at: " + ft + " " +
                    "and repeat based on expression: " + ReflectTool.on(triggerBuilder).call("getCronExpression"));
        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean stop() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
