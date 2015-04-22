package uk.co.dazcorp.android.holidaycards;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gentd on 21/04/2015.
 */
public class Utils {


    public static String loadJSONFromAsset(Context context, String fileName) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static int daysToGo(long date){
        LocalDate toDate = new LocalDate(date);
        LocalDate fromDate = LocalDate.now();

        return Days.daysBetween(fromDate, toDate).getDays();
    }
}
