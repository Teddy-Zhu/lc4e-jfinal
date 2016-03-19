package com.teddy.lc4e.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.lc4e.database.model.SysJob;

import java.util.List;

/**
 * Created by teddy on 2015/8/11.
 */
@Service
public class JobService {

    public static JobService service;

    @Cache(key = "allJobs")
    public List<SysJob> getAllJobs() {
        return SysJob.dao.findAll();
    }
}
