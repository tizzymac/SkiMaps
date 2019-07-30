package tizzy.skimapp.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import tizzy.skimapp.Emergency.EmergencyFragment;
import tizzy.skimapp.GetInfo.InfoListFragment;
import tizzy.skimapp.R;
import tizzy.skimapp.RouteFinding.DirectionsFragment;
import tizzy.skimapp.Settings.SettingsActivity;

public class PagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private SharedPreferences mSharedPref;

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, PagerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(
                new FragmentStatePagerAdapter(fragmentManager) {

                    @Override
                    public int getCount() {
                        return 3;
                    }

                    @Override
                    public Fragment getItem(int position) {
                        switch (position) {
                            case 0 : return DirectionsFragment.newInstance(mSharedPref.getString(SettingsActivity.KEY_PREF_SKI_ABILITY, "Black"));
                            case 1 : return InfoListFragment.newInstance();
                            case 2 : return EmergencyFragment.newInstance();
                            default: return DirectionsFragment.newInstance(mSharedPref.getString(SettingsActivity.KEY_PREF_SKI_ABILITY, "Black"));
                        }
                    }
                });

        mTabLayout.getTabAt(0)
                .setIcon(R.drawable.ic_directions_icon)
                .setText("Directions");
        mTabLayout.getTabAt(1)
                .setIcon(R.drawable.ic_info_icon)
                .setText("Information");
        mTabLayout.getTabAt(2)
                .setIcon(R.drawable.ic_emergency_icon)
                .setText("Emergency");

    }

    @Override
    public void onBackPressed() { }
}
