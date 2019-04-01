package tizzy.skimapp.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by tizzy on 10/13/18.
 */

public class SettingsActivity extends Activity {

    public static final String KEY_PREF_SKI_ABILITY = "pref_ski_ability";
    public static final String KEY_PREF_REGION = "pref_region";

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
