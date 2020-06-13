package uk.co.dazcorp.android.holidaycards;

import android.app.Activity;
import android.os.Bundle;
import androidx.core.widget.ContentLoadingProgressBar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.dazcorp.android.holidaycards.api.HolidayInterface;
import uk.co.dazcorp.android.holidaycards.data.Holiday;


public class MainActivity extends Activity {

    private HolidayInterface mHolidaysInterface;

    private ContentLoadingProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mHolidaysInterface = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/DazzyG/HolidayCards/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HolidayInterface.class);

        mHolidaysInterface.getHolidays().enqueue(new HolidayCallback());
        mLoading = findViewById(R.id.loading_view);
        mLoading.show();

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

    class HolidayCallback implements Callback<Holiday[]> {

        @Override
        public void onResponse(@NotNull Call<Holiday[]> call, Response<Holiday[]> response) {
            HolidayFragment mHolidayFragment = null;
            Holiday[] body = response.body();
            if (body != null) {
                mHolidayFragment = HolidayFragment.newInstance(body[0]);
            }
            getFragmentManager().beginTransaction().add(R.id.fragment_container, mHolidayFragment, "holiday").commit();
            mLoading.hide();
        }

        @Override
        public void onFailure(@NotNull Call<Holiday[]> call, @NotNull Throwable t) {
            // TODO: Error Handling
            Log.e("HOLIDAYCARDS", "Error", t);
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            mLoading.hide();
        }
    }
}
