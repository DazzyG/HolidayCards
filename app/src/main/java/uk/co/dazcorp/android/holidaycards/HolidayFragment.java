package uk.co.dazcorp.android.holidaycards;

import com.google.gson.Gson;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import uk.co.dazcorp.android.holidaycards.data.Holiday;
import uk.co.dazcorp.android.holidaycards.data.Photo;
import uk.co.dazcorp.android.holidaycards.data.Weather;



/**
 * Created by gentd on 23/04/2015.
 */
public class HolidayFragment extends Fragment implements CardContainer.CardChangedListener {

    private static final String HOLIDAY_OBJECT = "holidayObj";
    private Holiday mHoliday;
    private PhotoAdapter mPhotoAdapter;
    private CardContainer mPhotoContainer;
    private TextView mLocation;
    private TextView mTemp;
    private ImageView mTempIcon;
    private TextView mDaysToGo;
    private TextView mPhotoIndex;
    private BackgroundImageTarget mBackgroundImageTarget;

    public HolidayFragment() {
    }

    public static HolidayFragment newInstance(Holiday holiday) {
        HolidayFragment fragment = new HolidayFragment();
        Bundle args = new Bundle();
        args.putString(HOLIDAY_OBJECT, new Gson().toJson(holiday));
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mHoliday = new Gson().fromJson(args.getString(HOLIDAY_OBJECT), Holiday.class);
        }

        mPhotoAdapter = new PhotoAdapter(getActivity(), 0, new ArrayList<Photo>());
        mBackgroundImageTarget = new BackgroundImageTarget();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.holiday_fragment, container, false);

        mLocation = (TextView) rootView.findViewById(R.id.location_textview_value);
        mTemp = (TextView) rootView.findViewById(R.id.temp_textview_value);
        mTempIcon = (ImageView) rootView.findViewById(R.id.temp_icon_imageview);
        mDaysToGo = (TextView) rootView.findViewById(R.id.days_textview_value);
        mPhotoIndex = (TextView) rootView.findViewById(R.id.counter_textview);

        mPhotoContainer = (CardContainer) rootView.findViewById(R.id.photo_container);
        mPhotoContainer.setCardChangedListener(this);

        populateViews();

        return rootView;
    }

    private void populateViews() {

        if (mHoliday != null) {
            mDaysToGo.setText(String.valueOf(Utils.daysToGo(mHoliday.date)));
            if (mHoliday.weather != null) {
                populateWeather(mHoliday.weather);
            }
            if (mHoliday.photos != null) {
                populatePhotos(mHoliday.photos);
            }
        }

    }

    private void populatePhotos(Photo[] photos) {
        mPhotoAdapter.clear();
        mPhotoAdapter.addAll(Arrays.asList(photos));
        mPhotoContainer.setAdapter(mPhotoAdapter);
        updateIndex(0);
    }

    private void updateIndex(final int currentCard) {
        mPhotoIndex.setText(currentCard + 1 + " of " + mPhotoAdapter.getCount());
        Picasso.get().load(mPhotoAdapter.getItem(currentCard).url).into(mBackgroundImageTarget);
    }


    private void populateWeather(Weather weather) {
        mLocation.setText(weather.location);
        mTemp.setText(weather.temp_c);
        switch (weather.outlook) {
            case "SUNNY":
                mTempIcon.setImageResource(R.drawable.weather_sunny);
                break;
            default:
                mTempIcon.setImageResource(R.drawable.weather_rainy);
                break;
        }

    }

    @Override
    public void onCurrentCardChanged(int currentCard) {
        Picasso.get().cancelRequest(mBackgroundImageTarget);
        updateIndex(currentCard);
    }

    class BackgroundImageTarget implements Target {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mPhotoContainer.setBackground(new BitmapDrawable(getActivity().getResources(),
                    Utils.blurBitmap(getActivity(), mPhotoContainer, bitmap)));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

}
