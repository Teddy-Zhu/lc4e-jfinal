package com.teddy.jfinal.plugin.quartz;

import com.teddy.jfinal.interfaces.DBModel;

import java.util.List;

/**
 * Created by teddy on 2015/10/26.
 */
public interface IJob {

    List<? extends DBModel> getAllJobs();

    String getClassName();

    String getName();

    String getCron();
}
