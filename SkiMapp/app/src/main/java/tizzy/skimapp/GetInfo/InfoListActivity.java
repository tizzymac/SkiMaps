package tizzy.skimapp.GetInfo;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import tizzy.skimapp.SingleFragmentActivity;

public class InfoListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return InfoListFragment.newInstance();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, InfoListActivity.class);
        return intent;
    }
}
