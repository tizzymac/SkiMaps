package tizzy.skimapp.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import tizzy.skimapp.Emergency.EmergencyActivity;
import tizzy.skimapp.GetInfo.InfoListActivity;
import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.SkiLevel;
import tizzy.skimapp.RouteFinding.DirectionsActivity;
import tizzy.skimapp.Settings.SettingsActivity;

/**
 * Created by tizzy on 10/13/18.
 */

public class HomeFragment extends PreferenceFragment {

    private static final String TAG = "HomeFragment";

    private Button mDirectionsButton;
    private ImageButton mDirectionsIcon;
    private Button mInfoButton;
    private ImageButton mInfoIcon;
    // private Button mMapButton;
    private Button mEmergencyButton;
    private ImageButton mEmergencyIcon;
    private Button mSettingsButton;
    private ImageButton mSettingsIcon;

    private SharedPreferences mSharedPref;
    private TextView mSkiAbility;
    private ListView mListView;
    Resort mResort;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Create the resort
        //mResort = Resort.get(getActivity());
            // TODO only want to create it once when app is first opened

        // Load the preferences from an XML resource
        if (mResort.getRegion().equals("USA")) {
            addPreferencesFromResource(R.xml.preferences_usa);
        }
        if (mResort.getRegion().equals("Europe")) {
            addPreferencesFromResource(R.xml.preferences_eu);
        }

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        mDirectionsButton = view.findViewById(R.id.directionsButton);
        mDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open Route Finder
                Intent intent = DirectionsActivity.newIntent(getActivity(), mSharedPref.getString(SettingsActivity.KEY_PREF_SKI_ABILITY, "Black"));
                startActivity(intent);
            }
        });
        mDirectionsIcon = view.findViewById(R.id.directions_icon);
        mDirectionsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Route Finder
                Intent intent = DirectionsActivity.newIntent(getActivity(),
                        mSharedPref.getString(SettingsActivity.KEY_PREF_SKI_ABILITY, "Black"));
                startActivity(intent);
            }
        });

        mSettingsButton = view.findViewById(R.id.settingsButton);
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open Settings
                Intent intent = SettingsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        mSettingsIcon = view.findViewById(R.id.settings_icon);
        mSettingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open Settings
                Intent intent = SettingsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mInfoIcon = view.findViewById(R.id.info_icon);
        mInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open RunInfo for now
                Intent intent = InfoListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        mInfoButton = view.findViewById(R.id.infoButton);
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open RunInfo for now
                Intent intent = InfoListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mEmergencyButton = view.findViewById(R.id.emergencyButton);
        mEmergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmergencyActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        mEmergencyIcon = view.findViewById(R.id.emergency_icon);
        mEmergencyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmergencyActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mListView = view.findViewById(R.id.list_view);

        return view;
    }

    // TOOLBAR
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_settings_bar, menu);

        // Update the menu button
        MenuItem searchItem = menu.findItem(R.id.settings);
        searchItem.setEnabled(true);

    }


}
