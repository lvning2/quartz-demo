package com.zzsoft.quartzdemo.job;

import com.zzsoft.quartzdemo.service.DatabaseService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class BackupJob extends QuartzJobBean {

    private String databaseId;

    @Autowired
    private DatabaseService databaseService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        databaseService.backup(databaseId);
    }
}
