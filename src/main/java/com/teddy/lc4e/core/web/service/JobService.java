package com.teddy.lc4e.core.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.lc4e.core.database.model.Sys_Job;

import java.util.List;

/**
 * Created by teddy on 2015/8/11.
 */
@Service
public class JobService {

    public static JobService service;

    @Cache(key = "allJobs")
    public List<Sys_Job> getAllJobs() {
        return Sys_Job.dao.findAll();
    }
}
