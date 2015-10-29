package com.teddy.jfinal.plugin.quartz;

import com.teddy.jfinal.interfaces.DBModel;

import java.util.List;

/**
 * Created by teddy on 2015/10/26.
 */
public interface IJob {

    public List<? extends DBModel> getAllJobs();

    public String getClassName();

    public String getName();

    public String getCron();
}
