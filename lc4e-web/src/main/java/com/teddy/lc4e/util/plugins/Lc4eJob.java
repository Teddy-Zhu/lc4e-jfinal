package com.teddy.lc4e.util.plugins;

import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.jfinal.plugin.quartz.IJob;
import com.teddy.lc4e.database.mapping.T_Sys_Job;
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
        return T_Sys_Job.className;
    }

    @Override
    public String getName() {
        return T_Sys_Job.name;
    }

    @Override
    public String getCron() {
        return T_Sys_Job.cron;
    }
}
