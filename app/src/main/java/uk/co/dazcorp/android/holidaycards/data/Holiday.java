package uk.co.dazcorp.android.holidaycards.data;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by gentd on 21/04/2015.
 */
public class Holiday {

    public Long date;

    public Weather weather;

    public Photo[] photos;

    @NotNull
    @Override
    public String toString() {
        return "Holiday{" +
                "date=" + date +
                ", weather=" + weather +
                ", photos=" + Arrays.toString(photos) +
                '}';
    }
}
