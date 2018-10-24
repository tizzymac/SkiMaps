package tizzy.skimapp.GetInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import tizzy.skimapp.R;
import tizzy.skimapp.SingleFragmentActivity;

public class RunInfoActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new RunInfoFragment(); }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_run_info;
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RunInfoActivity.class);
        return intent;
    }
}
