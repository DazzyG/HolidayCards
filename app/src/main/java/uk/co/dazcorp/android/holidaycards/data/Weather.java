package uk.co.dazcorp.android.holidaycards.data;

import org.jetbrains.annotations.NotNull;

/**
 * Created by gentd on 21/04/2015.
 */
public class Weather {

    public String location;
    public String temp_c;
    public String outlook;

    @NotNull
    @Override
    public String toString() {
        return "Weather{" +
                "location='" + location + '\'' +
                ", temp_c='" + temp_c + '\'' +
                ", outlook='" + outlook + '\'' +
                '}';
    }
}
