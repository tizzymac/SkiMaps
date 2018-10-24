package tizzy.skimapp.GetInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import tizzy.skimapp.R;
import tizzy.skimapp.SingleFragmentActivity;

public class InfoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

//        // Where to put this?
//        Fragment liftListFragment = new LiftListFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.detail_fragment_container, liftListFragment)
//                .commit();

        return new RunListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_twopane;
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, InfoListActivity.class);
        return intent;
    }
}
