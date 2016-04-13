package com.teddy.jfinal.plugin;


import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.jfinal.plugin.quartz.IJob;
import com.teddy.jfinal.plugin.quartz.Lc4eJob;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
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
    private long sessionValidationInterval = 3600000L;

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
                        sys_job.getStr(jobService.getCron()), sys_job.getStr(jobService.getClassName()), sys_job.getStr(jobService.getGroup()));
                jobs.add(lc4eJob);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        CustomPlugin.getAnnotationsPack().getAnnotationClass(Job.class).forEach(aClass -> {
            com.teddy.jfinal.annotation.Job an = (com.teddy.jfinal.annotation.Job) aClass.getAnnotation(com.teddy.jfinal.annotation.Job.class);
            try {
                Lc4eJob job = new Lc4eJob((Job) aClass.newInstance(), an.name(), an.corn(), aClass.getName(), an.group());
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
            String jobName = lc4eJob.getName();
            String group = lc4eJob.getGroup();
            String jobCronExp = lc4eJob.getCorn();

            JobDetail jobDetail = JobBuilder.newJob(lc4eJob.getJob().getClass())
                    .withIdentity(jobName, group).build();
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger", group)
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(jobCronExp))
                    .build();

            try {
                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

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
