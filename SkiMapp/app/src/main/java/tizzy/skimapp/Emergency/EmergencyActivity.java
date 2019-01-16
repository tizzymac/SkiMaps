package tizzy.skimapp.Emergency;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import tizzy.skimapp.SingleFragmentActivity;

public class EmergencyActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EmergencyFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EmergencyActivity.class);
        return intent;
    }
}
