package tizzy.skimapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class RunInfoActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new RunInfoFragment(); }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RunInfoActivity.class);
        return intent;
    }
}
