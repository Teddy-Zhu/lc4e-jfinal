package com.teddy.jfinal.plugin.quartz;

import com.teddy.jfinal.interfaces.DBModel;
import org.quartz.Job;

import java.util.List;

/**
 * Created by teddy on 2015/10/26.
 */
public interface IJob extends Job{

    List<? extends DBModel> getAllJobs();

    String getClassName();

    String getName();

    String getCron();

    String getGroup();
}
