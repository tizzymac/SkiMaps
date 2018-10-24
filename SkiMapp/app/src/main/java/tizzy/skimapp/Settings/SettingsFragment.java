package tizzy.skimapp.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tizzy.skimapp.HomeActivity;
import tizzy.skimapp.R;

/**
 * Created by tizzy on 10/13/18.
 */

public class SettingsFragment extends PreferenceFragment {

    private Button setLevelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        setLevelButton = view.findViewById(R.id.set_level_button);
        setLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Return home
                Intent intent = HomeActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        return view;
    }



}
