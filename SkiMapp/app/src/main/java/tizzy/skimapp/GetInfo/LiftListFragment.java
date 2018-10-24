package tizzy.skimapp.GetInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

public class LiftListFragment extends Fragment {

    private RecyclerView mLiftRecyclerView;
    private LiftAdapter mLiftAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run_info, container, false);

        mLiftRecyclerView = view.findViewById(R.id.lift_info_recycler_view);
        mLiftRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        Resort resort = Resort.get(getActivity());
        List<Lift> lifts = resort.getLifts();

        mLiftAdapter = new LiftAdapter(lifts);
        mLiftRecyclerView.setAdapter(mLiftAdapter);
    }

    private class LiftHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mLiftNameTextView;
        private Lift mLift;

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mLift.getName() + " clicked!", Toast.LENGTH_SHORT).show();
        }

        public LiftHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_lift, parent, false));
            itemView.setOnClickListener(this);

            mLiftNameTextView = itemView.findViewById(R.id.lift_name);
        }

        public void bind(Lift lift) {
            mLift = lift;
            mLiftNameTextView.setText("Lift");
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
