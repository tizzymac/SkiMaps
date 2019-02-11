package tizzy.skimapp.RouteFinding.NavMode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.RouteFinding.SkiRoute;
import tizzy.skimapp.SingleFragmentActivity;

public class NavModeActivity extends SingleFragmentActivity {

    private static final String EXTRA_RESORT = "tizzy.skimapp.resort";
    private static final String EXTRA_ROUTE = "tizzy.skimapp.route";

    @Override
    protected Fragment createFragment() {
        Resort resort = (Resort) getIntent().getSerializableExtra(EXTRA_RESORT);
        SkiRoute route = (SkiRoute) getIntent().getSerializableExtra(EXTRA_ROUTE);
        return NavModeFragment.newInstance(resort, route);
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context packageContext, Resort resort, SkiRoute route) {
        Intent intent = new Intent(packageContext, NavModeActivity.class);
        intent.putExtra(EXTRA_RESORT, resort);
        intent.putExtra(EXTRA_ROUTE, route);
        return intent;
    }
}
