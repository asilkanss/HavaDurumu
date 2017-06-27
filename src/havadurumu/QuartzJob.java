/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package havadurumu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Asilkan
 */
public class QuartzJob implements Job {

    public static JSONObject objmin, objmax;
    public static double min, max;
    public static String tarih,Ad;
    public static String aydin = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/317040?apikey=Nv5EwjVm7Rf67TLEL12vO55SXOc9sNga";
    public static String istanbul = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/318251?apikey=Nv5EwjVm7Rf67TLEL12vO55SXOc9sNga";

    public static void hdurumu() {

    }
    //url api okumak için fonksiyon

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        paralel R1 = new paralel(aydin, "Aydın");
        R1.start();
        paralel R2 = new paralel(istanbul, "İstanbul");
        R2.start();

        SimpleDateFormat tarihformati = new SimpleDateFormat("dd.MM.yyyy 'saat' HH:mm:ss");
        tarihformati.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        tarih = tarihformati.format(new Date());
        System.out.println(tarih + " tarihinde hava durumu: ");
        System.out.println("Minimum: " + objmin.get("Value") + " ve " + "Maximum " + objmax.get("Value") + " dır.");
        // Add new user
        Hdurumu hdd = new Hdurumu();
        hdd.setMax(max);
        hdd.setMin(min);
        hdd.setTime(tarih);
        hdd.setLocation(Ad);
        addUser(hdd);
    }

    public void addUser(Hdurumu hd) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(hd);
            System.out.println("Kayıt başarılı");
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }
}
