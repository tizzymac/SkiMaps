package tizzy.skimapp.GetInfo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

public class InfoListFragment extends Fragment {

    private static final String DIALOG_RUN_DETAIL = "DialogRunDetail";
    private static final String DIALOG_LIFT_DETAIL = "DialogLiftDetail";

    private Resort mResort;
    private RecyclerView mRunRecyclerView;
    private RecyclerView mLiftRecyclerView;
    private RunAdapter mRunAdapter;
    private LiftAdapter mLiftAdapter;

    public static InfoListFragment newInstance() {
        Bundle args = new Bundle();
        InfoListFragment fragment = new InfoListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get reference to Resort
        mResort = Resort.get(getActivity());
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
        private LinearLayout mRunSubItem;
        private Run mRun;

        @Override
        public void onClick(View view) {
//            // Open details
//            FragmentManager manager = getFragmentManager();
//            RunDetailFragment runDialog = RunDetailFragment.newInstance(mRun);
//            runDialog.show(manager, DIALOG_RUN_DETAIL);

            // Expand to show details
            // Get the current state of the item
            boolean expanded = mRun.isExpanded();
            // Change the state
            mRun.setExpanded(!expanded);
            // Notify the adapter that item has changed
            mRunAdapter.notifyItemChanged(getLayoutPosition());
        }

        public RunHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_run, parent, false));
            itemView.setOnClickListener(this);

            mRunNameTextView = itemView.findViewById(R.id.run_name);
            mRunLevelImageView = itemView.findViewById(R.id.run_level);
            mRunSubItem = itemView.findViewById(R.id.run_sub_item);
        }

        public void bind(Run run) {
            mRun = run;
            mRunNameTextView.setText(mRun.getName());
            switch (mRun.getLevel().getLevelString()) {
                case ("Green"): mRunLevelImageView.setImageResource(R.drawable.ic_green);
                                break;
                case ("Blue"):  mRunLevelImageView.setImageResource(R.drawable.ic_blue);
                                break;
                case ("Black"): mRunLevelImageView.setImageResource(R.drawable.ic_black);
                                break;
            }
            mRunLevelImageView.setVisibility(View.VISIBLE);

            // Set the visibility based on state
            boolean expanded = mRun.isExpanded();
            mRunSubItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            TextView level = itemView.findViewById(R.id.run_sub_item_level);
            level.setText("Level: " + mRun.getLevel().getLevelString());

            TextView open =  itemView.findViewById(R.id.run_sub_item_open);
            open.setText("Open: " + mRun.getStatus().isOpen());

            TextView groomed = itemView.findViewById(R.id.run_sub_item_groomed);
            groomed.setText("Groomed: " + mRun.getStatus().isGroomed());
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
