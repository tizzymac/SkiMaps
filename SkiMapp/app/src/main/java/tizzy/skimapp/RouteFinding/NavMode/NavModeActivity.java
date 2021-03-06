package tizzy.skimapp.RouteFinding.NavMode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import tizzy.skimapp.ResortModel.SkiLevel;
import tizzy.skimapp.RouteFinding.SkiRoute.SkiRoute;
import tizzy.skimapp.SingleFragmentActivity;

public class NavModeActivity extends SingleFragmentActivity {

    private static final String EXTRA_ROUTE = "tizzy.skimapp.route";
    private static final String EXTRA_LEVEL = "tizzy.skimapp.skiLevel";
    private static final int MY_DATA_CHECK_CODE = 0;

    @Override
    protected Fragment createFragment() {
        SkiRoute route = (SkiRoute) getIntent().getSerializableExtra(EXTRA_ROUTE);
        SkiLevel level = (SkiLevel) getIntent().getSerializableExtra(EXTRA_LEVEL);
        return NavModeFragment.newInstance(route, level);
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent checkIntent = new Intent();
//        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }

    public static Intent newIntent(Context packageContext, SkiRoute route, SkiLevel level) {
        Intent intent = new Intent(packageContext, NavModeActivity.class);
        intent.putExtra(EXTRA_ROUTE, route);
        intent.putExtra(EXTRA_LEVEL, level);
        return intent;
    }

    @Override
    public void onBackPressed() {
        //
    }
}
