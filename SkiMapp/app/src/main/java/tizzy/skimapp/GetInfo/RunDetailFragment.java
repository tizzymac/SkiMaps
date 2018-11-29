package tizzy.skimapp.GetInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Run;
import tizzy.skimapp.ResortModel.RunStatus;

public class RunDetailFragment extends DialogFragment {

    public static final String ARG_RUN = "run";

    private static String mRunName;
    private static String mRunLevel;
    private static RunStatus mRunStatus;

    private TextView mRunNameView;
    private TextView mRunLevelView;
    private TextView mOpenView;
    private TextView mGroomView;

    public static RunDetailFragment newInstance(Run run) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RUN, run);

        mRunName = run.getName();
        mRunLevel = run.getLevel();
        mRunStatus = run.getStatus();

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

        mOpenView = view.findViewById(R.id.open);
        if (mRunStatus.isOpen()) {
            mOpenView.setText("Open: Yes");
        } else {
            mOpenView.setText("Open: No");
        }

        mGroomView = view.findViewById(R.id.groomed);
        if (mRunStatus.isGroomed()) {
            mGroomView.setText("Groom Status: Groomed");
        } else {
            mGroomView.setText("Groom Status: Not Groomed");
        }

        return alert;
    }
}
