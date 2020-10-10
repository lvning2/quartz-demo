package com.zzsoft.quartzdemo.job;

import com.zzsoft.quartzdemo.entity.Database;
import com.zzsoft.quartzdemo.repository.DatabaseRepository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class StartBackup implements ApplicationRunner {

    @Autowired
    DatabaseRepository databaseRepository;

    private static String backupGroup="backup-group";

    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduler = StdSchedulerFactory.getDefaultScheduler();

        List<Database> all = databaseRepository.findAll();
        for (Database database:all){
            JobDetail jobDetail = getJobDetail(database.getId());
            Trigger trigger = getTrigger(database.getId(), database.getCron());
            scheduler.addJob(jobDetail,true);
        }

        System.out.println("初始化数据库备份计划完成...");
    }


    public JobDetail getJobDetail(String id){    // job 名称为 数据库id
        return JobBuilder.newJob(BackupJob.class)
                .withIdentity(id,backupGroup)
                .usingJobData("databaseId",id)
                .storeDurably()
                .build();
    }

    public Trigger getTrigger(String id,String cron){  // trigger 名称为 数据库id
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        return TriggerBuilder.newTrigger().forJob(id)
                .withIdentity(id)
                .withSchedule(cronScheduleBuilder).startAt(new Date())
                .build();
    }

}
