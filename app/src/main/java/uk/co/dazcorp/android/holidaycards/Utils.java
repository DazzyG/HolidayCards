package uk.co.dazcorp.android.holidaycards;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

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


    public static int daysToGo(long date) {
        LocalDate toDate = new LocalDate(date);
        LocalDate fromDate = LocalDate.now();

        return Days.daysBetween(fromDate, toDate).getDays();
    }

    /**
     * Blurring code from Android ProTips
     * Modified by gentd
     */
    public static Bitmap blurBitmap(Context context, View view, Bitmap bitmap) {

        //Let's create an empty bitmap with the same size of the view we want to fit
        Bitmap outBitmap = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context);

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;


    }
}
