package tizzy.skimapp.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import tizzy.skimapp.R;

public class LevelExplanationFragment extends DialogFragment {

    public static LevelExplanationFragment newInstance() {
        Bundle args = new Bundle();
        LevelExplanationFragment fragment = new LevelExplanationFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_level, null);

        final AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .create();

        return alert;
    }
}
