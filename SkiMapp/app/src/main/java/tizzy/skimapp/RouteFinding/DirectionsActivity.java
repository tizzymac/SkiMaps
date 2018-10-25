package tizzy.skimapp.RouteFinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.SingleFragmentActivity;

public class DirectionsActivity extends SingleFragmentActivity {
    private static final String EXTRA_RESORT = "tizzy.skimapp.resort";

    @Override
    protected Fragment createFragment() {
        Resort resort = (Resort) getIntent().getSerializableExtra(EXTRA_RESORT);
        return DirectionsFragment.newInstance(resort);
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context packageContext, Resort resort) {
        Intent intent = new Intent(packageContext, DirectionsActivity.class);
        intent.putExtra(EXTRA_RESORT, resort);
        return intent;
    }
}
