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

//        // Where to put this?
//        Fragment liftListFragment = new LiftListFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.detail_fragment_container, liftListFragment)
//                .commit();

        Resort resort = (Resort) getIntent().getSerializableExtra(EXTRA_RESORT);

        return RunListFragment.newInstance(resort);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_twopane;
    }

    public static Intent newIntent(Context packageContext, Resort resort) {
        Intent intent = new Intent(packageContext, InfoListActivity.class);
        intent.putExtra(EXTRA_RESORT, resort);
        return intent;
    }
}
