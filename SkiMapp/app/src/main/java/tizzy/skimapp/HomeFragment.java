package tizzy.skimapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tizzy on 10/13/18.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private TextView skiAbility;
    private Button settingsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        settingsButton = view.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open Settings
                Intent intent = SettingsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        skiAbility = view.findViewById(R.id.skiAbility);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        skiAbility.setText(sharedPref.getString(SettingsActivity.KEY_PREF_SKI_ABILITY, "Black"));

        return view;
    }


}
