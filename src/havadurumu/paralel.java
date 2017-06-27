/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package havadurumu;

import static havadurumu.QuartzJob.max;
import static havadurumu.QuartzJob.min;
import static havadurumu.QuartzJob.objmax;
import static havadurumu.QuartzJob.objmin;
import static havadurumu.QuartzJob.Ad;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Asilkan
 */
public class paralel extends Thread {

    private Thread t;
    private String sehir, sehirAdi;

    paralel(String name, String isim) {
        sehir = name;
        sehirAdi = isim;
        System.out.println("Creating " + sehirAdi);
    }

    public void run() {
        System.out.println("Running " + sehirAdi);
        try {
            String url = readUrl(sehir);
            JSONObject jsonobj = (JSONObject) new JSONParser().parse(url);
            JSONArray dizi = (JSONArray) jsonobj.get("DailyForecasts");
            JSONObject obj = (JSONObject) dizi.get(0);
            JSONObject obj2 = (JSONObject) obj.get("Temperature");
            Ad=sehirAdi;
            //link = (String) obj.get("Link");
            objmin = (JSONObject) obj2.get("Minimum");
            objmax = (JSONObject) obj2.get("Maximum");
            min = (5.0 / 9.0) * ((double) objmin.get("Value") - 32.0);
            max = (5.0 / 9.0) * ((double) objmax.get("Value") - 32.0);
            System.out.println("Selam,\n" + sehirAdi + " için ");
            System.out.println(" Min: " + min + " derece\nMax: " + max + " derecedir.");

            // Let the thread sleep for a while.
            Thread.sleep(50);

        } catch (Exception e) {
            System.out.println("Thread " + sehirAdi + " interrupted.");
        }
        System.out.println("Thread " + sehirAdi + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + sehirAdi);
        if (t == null) {
            t = new Thread(this, sehir);
            t.start();
        }
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
