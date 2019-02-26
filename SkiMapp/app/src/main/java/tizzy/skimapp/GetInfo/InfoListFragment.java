package tizzy.skimapp.GetInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

public class InfoListFragment extends Fragment {

    private static final String DIALOG_RUN_DETAIL = "DialogRunDetail";
    private static final String DIALOG_LIFT_DETAIL = "DialogLiftDetail";
    private static final String ARG_RESORT = "resort";
    private Resort mResort;

    private RecyclerView mRunRecyclerView;
    private RecyclerView mLiftRecyclerView;
    private RunAdapter mRunAdapter;
    private LiftAdapter mLiftAdapter;

    public static InfoListFragment newInstance(Resort resort) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESORT, resort);
        InfoListFragment fragment = new InfoListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResort = (Resort) getArguments().getSerializable(ARG_RESORT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        mRunRecyclerView = view.findViewById(R.id.run_info_recycler_view);
        mRunRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLiftRecyclerView = view.findViewById(R.id.lift_info_recycler_view);
        mLiftRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        mRunAdapter = new RunAdapter(mResort.getRuns());
        mRunRecyclerView.setAdapter(mRunAdapter);

        mLiftAdapter = new LiftAdapter(mResort.getLifts());
        mLiftRecyclerView.setAdapter(mLiftAdapter);
    }

    private class RunHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRunNameTextView;
        private ImageView mRunLevelImageView;
        private Run mRun;

        @Override
        public void onClick(View view) {
            // Open details
            FragmentManager manager = getFragmentManager();
            RunDetailFragment runDialog = RunDetailFragment.newInstance(mRun);
            runDialog.show(manager, DIALOG_RUN_DETAIL);
        }

        public RunHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_run, parent, false));
            itemView.setOnClickListener(this);

            mRunNameTextView = itemView.findViewById(R.id.run_name);
            mRunLevelImageView = itemView.findViewById(R.id.run_level);
        }

        public void bind(Run run) {
            mRun = run;
            mRunNameTextView.setText(mRun.getName());
            switch (mRun.getLevel()) {
                case ("Green"): mRunLevelImageView.setImageResource(R.mipmap.ic_green);
                                break;
                case ("Blue"):  mRunLevelImageView.setImageResource(R.mipmap.ic_blue);
                                break;
                case ("Black"): mRunLevelImageView.setImageResource(R.mipmap.ic_black);
                                break;
            }
            mRunLevelImageView.setVisibility(View.VISIBLE);
        }
    }

    private class RunAdapter extends RecyclerView.Adapter<RunHolder> {
        private List<Run> mRuns;

        public RunAdapter(List<Run> runs) {
            mRuns = runs;
        }

        @Override
        public RunHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RunHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(RunHolder holder, int position) {
            Run run = mRuns.get(position);
            holder.bind(run);
        }

        @Override
        public int getItemCount() {
            return mRuns.size();
        }
    }

    private class LiftHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mLiftNameTextView;
        private Lift mLift;

        @Override
        public void onClick(View view) {
            // Open details
            FragmentManager manager = getFragmentManager();
            LiftDetailFragment liftDialog = LiftDetailFragment.newInstance(mLift);
            liftDialog.show(manager, DIALOG_LIFT_DETAIL);
        }

        public LiftHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_lift, parent, false));
            itemView.setOnClickListener(this);

            mLiftNameTextView = itemView.findViewById(R.id.lift_name);
        }

        public void bind(Lift lift) {
            mLift = lift;
            mLiftNameTextView.setText(mLift.getName());
        }
    }

    private class LiftAdapter extends RecyclerView.Adapter<LiftHolder> {
        private List<Lift> mLifts;

        public LiftAdapter(List<Lift> lifts) {
            mLifts = lifts;
        }

        @Override
        public LiftHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new LiftHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(LiftHolder holder, int position) {
            Lift lift = mLifts.get(position);
            holder.bind(lift);
        }

        @Override
        public int getItemCount() {
            return mLifts.size();
        }
    }
}
