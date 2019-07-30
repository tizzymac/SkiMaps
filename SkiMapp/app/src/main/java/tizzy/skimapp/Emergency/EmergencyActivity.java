package tizzy.skimapp.Emergency;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import tizzy.skimapp.SingleFragmentActivity;

public class EmergencyActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return EmergencyFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EmergencyActivity.class);
        return intent;
    }
}
