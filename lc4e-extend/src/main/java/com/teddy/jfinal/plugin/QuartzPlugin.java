package com.teddy.jfinal.plugin;


import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.jfinal.plugin.quartz.IJob;
import com.teddy.jfinal.plugin.quartz.Lc4eJob;
import com.teddy.jfinal.tools.ReflectTool;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuartzPlugin implements IPlugin {
    private static Log log;
    private Set<Lc4eJob> jobs;
    private SchedulerFactory sf;
    private Scheduler scheduler;

    private IJob jobService;
    public QuartzPlugin() {
    }

    public QuartzPlugin add(Lc4eJob job) {
        jobs.add(job);
        return this;
    }

    @Override
    public boolean start() {
        loadJobs();

        startJobs();
        return true;
    }

    private void loadJobs() {
        jobs = new HashSet<>();
        List<? extends DBModel> allJobs = jobService.getAllJobs();
        allJobs.forEach(sys_job -> {
            Class<Job> job = ReflectTool.on(sys_job.getStr(jobService.getClassName())).get();
            try {
                Lc4eJob lc4eJob = new Lc4eJob(job.newInstance(), sys_job.getStr(jobService.getName()),
                        sys_job.getStr(jobService.getCron()), sys_job.getStr(jobService.getClassName()));
                jobs.add(lc4eJob);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        CustomPlugin.getAnnotationsPack().getAnnotationClass(Job.class).forEach(aClass -> {
            com.teddy.jfinal.annotation.Job an = (com.teddy.jfinal.annotation.Job) aClass.getAnnotation(com.teddy.jfinal.annotation.Job.class);
            try {
                Lc4eJob job = new Lc4eJob((Job) aClass.newInstance(), an.name(), an.corn(), aClass.getName());
                jobs.add(job);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }

    private void startJobs() {
        try {
            sf = new StdSchedulerFactory();
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        jobs.forEach(lc4eJob -> {
            String jobClassName = lc4eJob.getClassName();
            String jobCronExp = lc4eJob.getName();
            JobBuilder jobBuilder;
            TriggerBuilder triggerBuilder;

            jobBuilder = ReflectTool.on("org.quartz.JobBuilder").call("newJob", lc4eJob.getJob().getClass()).call("withIdentity", jobClassName, jobClassName)
                    .call("build").get();
            Object temp = ReflectTool.on("org.quartz.TriggerBuilder").call("newTrigger").get();
            temp = ReflectTool.on(temp).call("withIdentity", jobClassName, jobClassName).get();
            temp = ReflectTool.on(temp).call("withSchedule",
                    ReflectTool.on("org.quartz.CronScheduleBuilder").call("cronSchedule", jobCronExp).get())
                    .get();
            triggerBuilder = ReflectTool.on(temp).call("build").get();

            Date ft = ReflectTool.on(scheduler).call("scheduleJob", jobBuilder, triggerBuilder).get();
            log.debug(ReflectTool.on(jobBuilder).call("getKey") + " has been scheduled to run at: " + ft + " " +
                    "and repeat based on expression: " + ReflectTool.on(triggerBuilder).call("getCronExpression"));
        });

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
