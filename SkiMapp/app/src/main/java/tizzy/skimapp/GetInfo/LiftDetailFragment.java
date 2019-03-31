package tizzy.skimapp.GetInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.LiftStatus;
import tizzy.skimapp.ResortModel.RunStatus;

public class LiftDetailFragment extends DialogFragment {

    public static final String ARG_LIFT = "lift";

    private static String mLiftName;
    private static LiftStatus mLiftStatus;
    private static String mLiftCapacity;
    private static String mOpenTime;
    private static String mCloseTime;

    private TextView mLiftNameView;
    private TextView mOpenView;
    private TextView mCapacityView;
    private TextView mOpenTimeView;
    private TextView mCloseTimeView;

    public static LiftDetailFragment newInstance(Lift lift) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIFT, lift);

        mLiftName = lift.getName();
        mLiftStatus = lift.getStatus();
        mLiftCapacity = "Capacity: " + String.valueOf(lift.getCapacity());
        mOpenTime = "Opens at: " + lift.getStatus().openingTimeStr();
        mCloseTime = "Closes at: " + lift.getStatus().closingTimeStr();

        LiftDetailFragment fragment = new LiftDetailFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_lift, null);

        final AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .create();

        mLiftNameView = view.findViewById(R.id.lift_name);
        mLiftNameView.setText(mLiftName);

        mOpenView = view.findViewById(R.id.open);
        if (mLiftStatus.isOpen()) {
            mOpenView.setText("Open: Yes");
        } else {
            mOpenView.setText("Open: No");
        }

        mCapacityView = view.findViewById(R.id.capacity);
        mCapacityView.setText(mLiftCapacity);

        mOpenTimeView = view.findViewById(R.id.open_time);
        mOpenTimeView.setText(mOpenTime);

        mCloseTimeView = view.findViewById(R.id.close_time);
        mCloseTimeView.setText(mCloseTime);

    return alert;
    }

}
