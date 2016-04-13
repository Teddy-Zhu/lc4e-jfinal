package com.teddy.lc4e.util.plugins;

import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.jfinal.plugin.quartz.IJob;
import com.teddy.lc4e.database.model.SysJob;
import com.teddy.lc4e.web.service.JobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
        return SysJob.CLASSNAME;
    }

    @Override
    public String getName() {
        return SysJob.NAME;
    }

    @Override
    public String getCron() {
        return SysJob.CRON;
    }

    @Override
    public String getGroup() {
        return SysJob.GROUP;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Not Implement");
    }
}
