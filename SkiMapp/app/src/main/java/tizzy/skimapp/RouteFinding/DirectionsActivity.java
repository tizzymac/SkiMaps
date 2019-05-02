package tizzy.skimapp.RouteFinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.SingleFragmentActivity;

public class DirectionsActivity extends SingleFragmentActivity {
    private static final String EXTRA_SKI_ABILITY = "tizzy.skimapp.ski_ability";

    @Override
    protected Fragment createFragment() {
        String skiAbility = (String) getIntent().getSerializableExtra(EXTRA_SKI_ABILITY);
        return DirectionsFragment.newInstance(skiAbility);
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context packageContext, String skiAbility) {
        Intent intent = new Intent(packageContext, DirectionsActivity.class);
        intent.putExtra(EXTRA_SKI_ABILITY, skiAbility);
        return intent;
    }

    @Override
    public void onBackPressed() {
        //
    }
}
