package tizzy.skimapp.GetInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import tizzy.skimapp.R;

public class RunDetailFragment extends DialogFragment {

    public static final String ARG_RUN_NAME = "run_name";
    public static final String ARG_RUN_LEVEL = "run_level";

    private static String mRunName;
    private static String mRunLevel;
    private TextView mRunNameView;
    private TextView mRunLevelView;

    public static RunDetailFragment newInstance(String name, String level) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RUN_NAME, name);
        args.putSerializable(ARG_RUN_LEVEL, level);

        mRunName = name;
        mRunLevel = level;

        RunDetailFragment fragment = new RunDetailFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_run, null);

        final AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .create();

        mRunNameView = view.findViewById(R.id.run_name);
        mRunNameView.setText(mRunName);

        mRunLevelView = view.findViewById(R.id.run_level);
        mRunLevelView.setText("Level: " + mRunLevel);


        return alert;
    }
}
