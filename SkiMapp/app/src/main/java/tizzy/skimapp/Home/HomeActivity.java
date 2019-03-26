package tizzy.skimapp.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends Activity {

    private static final String EXTRA_RESORT = "tizzy.skimapp.resort";

//    @Override
//    protected Fragment createFragment() { return new HomeFragment(); }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new HomeFragment())
                .commit();
    }
}
