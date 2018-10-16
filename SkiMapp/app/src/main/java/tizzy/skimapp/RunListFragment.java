package tizzy.skimapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

public class RunListFragment extends Fragment {

    private RecyclerView mRunRecyclerView;
    private RunAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run_info, container, false);

        mRunRecyclerView = view.findViewById(R.id.run_info_recycler_view);
        mRunRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        Resort resort = Resort.get(getActivity());
        List<Run> runs = resort.getRuns();

        mAdapter = new RunAdapter(runs);
        mRunRecyclerView.setAdapter(mAdapter);
    }

    private class RunHolder extends RecyclerView.ViewHolder {

        private TextView mRunNameTextView;
        private TextView mRunLevelTextView;
        private Run mRun;

        public RunHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_run, parent, false));

            mRunNameTextView = itemView.findViewById(R.id.run_name);
            mRunLevelTextView = itemView.findViewById(R.id.run_level);
        }

        public void bind(Run run) {
            mRun = run;
            mRunLevelTextView.setText(mRun.getLevel());
            mRunNameTextView.setText(mRun.getName());
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
}
