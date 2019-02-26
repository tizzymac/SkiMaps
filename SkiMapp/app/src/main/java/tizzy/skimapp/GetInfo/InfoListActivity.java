package tizzy.skimapp.GetInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.SingleFragmentActivity;

public class InfoListActivity extends SingleFragmentActivity {

    private static final String EXTRA_RESORT = "tizzy.skimapp.resort";

    @Override
    protected Fragment createFragment() {

        Resort resort = (Resort) getIntent().getSerializableExtra(EXTRA_RESORT);

        return InfoListFragment.newInstance(resort);
    }

//    @Override
//    protected int getLayoutResId() {
//        return R.layout.activity_twopane;
//    }

    public static Intent newIntent(Context packageContext, Resort resort) {
        Intent intent = new Intent(packageContext, InfoListActivity.class);
        intent.putExtra(EXTRA_RESORT, resort);
        return intent;
    }
}
