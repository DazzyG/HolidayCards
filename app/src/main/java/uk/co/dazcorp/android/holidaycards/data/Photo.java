package uk.co.dazcorp.android.holidaycards.data;

import org.jetbrains.annotations.NotNull;

/**
 * Created by gentd on 21/04/2015.
 */
public class Photo {

    public String title;
    public String url;

    @NotNull
    @Override
    public String toString() {
        return "Photo{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
