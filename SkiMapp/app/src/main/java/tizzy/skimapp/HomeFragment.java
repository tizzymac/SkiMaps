package tizzy.skimapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tizzy.skimapp.GetInfo.InfoListActivity;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.RouteFinding.DirectionsActivity;
import tizzy.skimapp.Settings.SettingsActivity;

/**
 * Created by tizzy on 10/13/18.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private TextView mSkiAbility;
    private TextView mRunLevel;
    private Button mSettingsButton;
    private Button mInfoButton;
    private Button mDirectionsButton;

    Resort mResort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Create the resort
        mResort = Resort.get(getActivity());
            // TODO only want to create it once when app is first opened

        mDirectionsButton = view.findViewById(R.id.directionsButton);
        mDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Route Finder
                Intent intent = DirectionsActivity.newIntent(getActivity(), mResort);
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

        mInfoButton = view.findViewById(R.id.infoButton);
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open RunInfo for now
                Intent intent = InfoListActivity.newIntent(getActivity(), mResort);
                startActivity(intent);
            }
        });

        mSkiAbility = view.findViewById(R.id.skiAbility);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        mSkiAbility.setText(sharedPref.getString(SettingsActivity.KEY_PREF_SKI_ABILITY, "Black"));

        mRunLevel = view.findViewById(R.id.runLevel);
        mRunLevel.setText(
                // Get level from resort xml
                ""
        );

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
