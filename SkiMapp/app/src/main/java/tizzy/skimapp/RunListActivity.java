package tizzy.skimapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class RunListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new RunListFragment();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RunListActivity.class);
        return intent;
    }
}
