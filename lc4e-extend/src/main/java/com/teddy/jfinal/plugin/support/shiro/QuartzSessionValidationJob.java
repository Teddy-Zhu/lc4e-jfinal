package com.teddy.jfinal.plugin.support.shiro;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by teddyzhu on 16/4/13.
 */
public class QuartzSessionValidationJob implements Job {

    /**
     * Key used to store the session manager in the job data map for this job.
     */
    public static final String SESSION_MANAGER_KEY = "sessionManager";


    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationJob.class);


    /**
     * Called when the job is executed by quartz. This method delegates to the <tt>validateSessions()</tt> method on the
     * associated session manager.
     *
     * @param context the Quartz job execution context for this execution.
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);

        if (log.isDebugEnabled()) {
            log.debug("Executing session validation Quartz job...");
        }

        sessionManager.validateSessions();

        if (log.isDebugEnabled()) {
            log.debug("Session validation Quartz job complete.");
        }
    }
}
