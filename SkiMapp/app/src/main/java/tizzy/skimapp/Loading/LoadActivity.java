package tizzy.skimapp.Loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import tizzy.skimapp.SingleFragmentActivity;

public class LoadActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return LoadFragment.newInstance();
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
        Intent intent = new Intent(packageContext, LoadActivity.class);
        return intent;
    }
}
