package vn.edu.usth.weather;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(FragmentManager fa) {
        super(fa);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WeatherAndForecastFragment();
            case 1:
                return new WeatherAndForecastFragment_2();
            case 2:
                return new WeatherAndForecastFragment_3();
            default:
                return new WeatherAndForecastFragment();
        }
    }

    @Override
    public int getCount() {
        return 3; // Number of pages for a ViewPager
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "HANOI, VIETNAM";
                break;
            case 1:
                title = "HCM City, VIETNAM";
                break;
            case 2:
                title = "PARIS, FRANCE";
                break;
        }
        return title;
    }
}