package tizzy.skimapp.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import tizzy.skimapp.SingleFragmentActivity;


public class MapActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MapFragment();
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MapActivity.class);
        return intent;
    }
}
