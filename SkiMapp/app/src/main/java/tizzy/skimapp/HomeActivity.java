package tizzy.skimapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class HomeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new HomeFragment(); }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, HomeActivity.class);
        return intent;
    }
}
