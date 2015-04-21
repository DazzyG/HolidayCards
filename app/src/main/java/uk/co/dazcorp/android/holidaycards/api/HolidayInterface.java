package uk.co.dazcorp.android.holidaycards.api;

import retrofit.Callback;
import retrofit.http.GET;
import uk.co.dazcorp.android.holidaycards.data.Holiday;

/**
 * Created by gentd on 21/04/2015.
 */
public interface HolidayInterface {

    @GET("/")
    void getHoliday(Callback<Holiday> callback);

}
