/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package havadurumu;

import static havadurumu.QuartzJob.tarih;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HavaDurumu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            JobDetail job = JobBuilder.newJob(QuartzJob.class).build();
            //Trigger t1 = TriggerBuilder.newTrigger().withIdentity("SimpleTrigger").startNow().build();
            Trigger t1 = TriggerBuilder.newTrigger().withIdentity("CronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
            Scheduler sc = StdSchedulerFactory.getDefaultScheduler();
            sc.start();
            sc.scheduleJob(job, t1);
        } catch (Exception e) {

        }
    }
}
