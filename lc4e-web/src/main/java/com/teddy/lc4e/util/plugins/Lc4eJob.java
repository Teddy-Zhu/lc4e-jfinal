package com.teddy.lc4e.util.plugins;

import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.jfinal.plugin.quartz.IJob;
import com.teddy.lc4e.database.model.Sys_Job;
import com.teddy.lc4e.web.service.JobService;

import java.util.List;

/**
 * Created by teddy on 2015/10/26.
 */
public class Lc4eJob implements IJob {
    @Override
    public List<? extends DBModel> getAllJobs() {
        return JobService.service.getAllJobs();
    }

    @Override
    public String getClassName() {
        return Sys_Job.S_CLASSNAME;
    }

    @Override
    public String getName() {
        return Sys_Job.S_NAME;
    }

    @Override
    public String getCron() {
        return Sys_Job.S_CRON;
    }
}
