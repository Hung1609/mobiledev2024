package vn.edu.usth.weather;

import android.os.Bundle;
import android.os.Environment;
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
import android.content.Context;
import java.io.File;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import android.media.MediaPlayer;
import java.io.IOException;

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

        rawToSdcard(this,R.raw.mobile_app,"mobile_app.mp3");

        Audio("mobile_app.mp3");
    }

    public void rawToSdcard(Context context, int resourceId, String fileName){
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        File sdCardDir = Environment.getExternalStorageDirectory();
        File outFile = new File(sdCardDir, fileName);

        try {
            OutputStream outputStream = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer mediaPlayer;

    public void Audio(String fileName){
        File sdCardDir = Environment.getExternalStorageDirectory();
        File audioFile = new File(sdCardDir, fileName);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("WeatherActivity", "Error playing audio: " + e.getMessage());
            e.printStackTrace();
        }
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
}