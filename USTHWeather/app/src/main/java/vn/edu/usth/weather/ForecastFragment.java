package vn.edu.usth.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private LinearLayout forecastLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        LinearLayout li = new LinearLayout(getContext());
        li.setBackgroundColor(0x2000FFFF);
        li.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getContext());
        textView.setText("Thursday");

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.weather_icon);

        li.addView(textView);
        li.addView(imageView);

        return li;
         */
        View v = inflater.inflate(R.layout.fragment_forecast, container, false);

        forecastLayout = v.findViewById(R.id.forecast_layout);
        new DownImageTask().execute("https://usth.edu.vn/wp-content/uploads/2021/11/logo.png");

        return v;
    }

    private class DownImageTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap logoBitmap = null;
            try {
                // Initialize URL
                URL imageUrl = new URL(url);

                // Make a request to the server
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                // Receive the response code and log it
                int responseCode = connection.getResponseCode();
                Log.i("USTHWeather", "The response code is: " + responseCode);

                // Process the image response
                InputStream inputStream = connection.getInputStream();
                logoBitmap = BitmapFactory.decodeStream(inputStream);

                inputStream.close();
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return logoBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            if(bitmap!=null){
                Drawable drawable = new BitmapDrawable(getResources(),bitmap);
                forecastLayout.setBackground(drawable);
            }
        }
    }
}