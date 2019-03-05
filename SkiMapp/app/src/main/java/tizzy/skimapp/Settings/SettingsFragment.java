package tizzy.skimapp.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import tizzy.skimapp.HomeActivity;
import tizzy.skimapp.R;

/**
 * Created by tizzy on 10/13/18.
 */

public class SettingsFragment extends PreferenceFragment {

    private static final String DIALOG_LEVEL_EXPLANATIONS = "DialogLevelExplanations";

    private Button mSetLevelButton;
    private Button mWhatLevelButton;
    private ListView mListView;
    private TextView mLevelExplanations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mListView = view.findViewById(R.id.list_view);

        mSetLevelButton = view.findViewById(R.id.set_level_button);
        mSetLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Return home
                Intent intent = HomeActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mLevelExplanations = view.findViewById(R.id.level_explanations);
        mLevelExplanations.setTextColor(Color.parseColor("#304f86"));

        mWhatLevelButton = view.findViewById(R.id.what_level_button);
        mWhatLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open level explanations
//                FragmentManager manager = getFragmentManager();
//                LevelExplanationFragment runDialog = LevelExplanationFragment.newInstance();
//                runDialog.show(manager, DIALOG_LEVEL_EXPLANATIONS);

                // Show explanations
                mLevelExplanations.setTextColor(Color.parseColor("#304f86"));
            }
        });

        return view;
    }



}
