package com.teddy.jfinal.plugin;

import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;
import com.teddy.jfinal.plugin.quartz.Lc4eJob;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.lc4e.core.database.mapping.T_Sys_Job;
import com.teddy.lc4e.core.database.model.Sys_Job;
import com.teddy.lc4e.core.web.service.JobService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuartzPlugin implements IPlugin {
    private final Logger logger = Logger.getLogger(getClass());
    private Set<Lc4eJob> jobs;
    private SchedulerFactory sf;
    private Scheduler scheduler;

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
        List<Sys_Job> allJobs = JobService.service.getAllJobs();
        allJobs.forEach(sys_job -> {
            Class<Job> job = ReflectTool.on(sys_job.getStr(T_Sys_Job.className)).get();
            try {
                Lc4eJob lc4eJob = new Lc4eJob(job.newInstance(), sys_job.getStr(T_Sys_Job.name),
                        sys_job.getStr(T_Sys_Job.cron), sys_job.getStr(T_Sys_Job.className));
                jobs.add(lc4eJob);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        CustomPlugin.getClassesMap().get(Job.class).forEach(aClass -> {
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
            logger.debug(ReflectTool.on(jobBuilder).call("getKey") + " has been scheduled to run at: " + ft + " " +
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
