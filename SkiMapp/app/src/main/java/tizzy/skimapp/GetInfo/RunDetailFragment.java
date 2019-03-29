package tizzy.skimapp.GetInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Run;
import tizzy.skimapp.ResortModel.RunStatus;

public class RunDetailFragment extends DialogFragment {

    public static final String ARG_RUN = "run";

    private static String mRunName;
    private static String mRunLevel;
    private static RunStatus mRunStatus;
    private static int mRunSteepness;

    private TextView mRunNameView;
    private TextView mRunLevelView;
    private TextView mOpenView;
    private TextView mGroomView;
    //private TextView mSteepnessView;

    public static RunDetailFragment newInstance(Run run) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RUN, run);

        mRunName = run.getName();
        mRunLevel = run.getLevel();
        mRunStatus = run.getStatus();
        //mRunSteepness = run.getSlopeAngle();

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

        // TODO Access firebase to see if run is groomed

        mGroomView = view.findViewById(R.id.groomed);
        if (mRunStatus.isGroomed()) {
            mGroomView.setText("Groomed: Yes");
        } else {
            mGroomView.setText("Groomed: No");
        }

//        mSteepnessView = view.findViewById(R.id.steepness);
//        mSteepnessView.setText("Avg Slope Angle: " + Integer.toString(mRunSteepness));

        return alert;
    }

}
