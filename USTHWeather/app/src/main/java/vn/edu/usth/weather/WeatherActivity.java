package vn.edu.usth.weather;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WeatherActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather); // Set your layout here
        // Create Adapter
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        // Create and set ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        // Create and set TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Extract the MP3 file
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.on_and_on);
            File outputFile = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "on_and_on.mp3");
            OutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            // Initialize MediaPlayer
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(outputFile.getAbsolutePath());
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the ToolBar as the Action Bar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        Log.i("MyApp", "onCreate called"); // Log a message
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Start background tasks or register listeners
        Log.i("MyApp", "onStart called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Resume animations, start media playback, etc.
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        Log.i("MyApp", "onResume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Save data or release resources
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        Log.i("MyApp", "onPause called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up resources, close connections, etc.
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Log.i("MyApp", "onDestroy called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Handle the refresh action
        if (id == R.id.refresh) {
            refreshContent();
            return true;
        }
        // Handle the settings action
        if (id == R.id.settings) {
            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshContent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WeatherActivity.this, "some sample json here", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}