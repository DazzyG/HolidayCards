package uk.co.dazcorp.android.holidaycards.api;


import retrofit2.Call;
import retrofit2.http.GET;
import uk.co.dazcorp.android.holidaycards.data.Holiday;

/**
 * Created by gentd on 21/04/2015.
 */
public interface HolidayInterface {

    @GET("holidays")
    Call<Holiday[]> getHolidays();

}
