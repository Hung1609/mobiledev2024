package vn.edu.usth.weather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class WeatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ViewPager pager;
        pager = (ViewPager) findViewById(R.id.view_pager);

        TabLayout tabLayout;
        tabLayout = findViewById(R.id.tab);

        ViewPagerAdapter adapter;
        adapter= new ViewPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        /*
        // Create a new Fragment to be placed in the activity l
        ForecastFragment firstFragment = new ForecastFragment();
        // Add the fragment to the 'container' FrameLayout
        WeatherFragment secondFragment = new WeatherFragment();

        getFragmentManager().beginTransaction().add(R.id.fragment_forecast, firstFragment).commit();
        getFragmentManager().beginTransaction().add(R.id.fragment_weather, secondFragment).commit();
        */
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final String[] tabTitles = {"Hanoi", "HCM", "Paris"};
        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            return new WeatherAndForecastFragment();
        }

        @Override
        public int getCount(){
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
        }
    }

    /*
    public WeatherActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Weather", "onStart here");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Weather", "onStop here");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Weather", "onDestroy here");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Weather", "onPause here");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Weather", "onResume here");
    }
     */
}