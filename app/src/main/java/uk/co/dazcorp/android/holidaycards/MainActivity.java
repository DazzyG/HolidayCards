package uk.co.dazcorp.android.holidaycards;

import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.dazcorp.android.holidaycards.api.HolidayInterface;
import uk.co.dazcorp.android.holidaycards.data.Holiday;


public class MainActivity extends Activity {


    private RestAdapter mRestAdapter;
    private HolidayInterface mHolidaysInterface;
    private Holiday mHoliday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRestAdapter = new RestAdapter.Builder().setEndpoint("http://mock.end.point").build();
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(mRestAdapter);
        MockHolidaysInterface mockHolidays = new MockHolidaysInterface();

        mHolidaysInterface = mockRestAdapter.create(HolidayInterface.class, mockHolidays);

        mHolidaysInterface.getHoliday(new HolidayCallback());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MockHolidaysInterface implements HolidayInterface {

        @Override
        public void getHoliday(Callback<Holiday> callback) {
            Holiday holiday = new GsonBuilder().create()
                    .fromJson(Utils.loadJSONFromAsset(MainActivity.this, "holidays.json"), Holiday.class);
            
            callback.success(holiday, null);
        }
    }

    class HolidayCallback implements Callback<Holiday> {

        @Override
        public void success(Holiday holiday, Response response) {
            mHoliday = holiday;
            Toast.makeText(MainActivity.this, mHoliday.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void failure(RetrofitError error) {
            // TODO: Error Handling
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
        }
    }
}
