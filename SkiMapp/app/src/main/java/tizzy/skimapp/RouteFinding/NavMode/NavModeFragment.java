package tizzy.skimapp.RouteFinding.NavMode;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.RouteFinding.SkiRoute;
import tizzy.skimapp.RouteFinding.SkiersLocation;

public class NavModeFragment extends Fragment {
    private static final String ARG_RESORT = "resort";
    private static final String ARG_ROUTE = "route";

    private Resort mResort;
    private SkiersLocation mSkiersLocation;
    LocationManager locationManager;
    private SkiRoute mSkiRoute;
    private RecyclerView mRecyclerView;

    public static NavModeFragment newInstance(Resort resort, SkiRoute route) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESORT, resort);
        args.putSerializable(ARG_ROUTE, route);
        NavModeFragment fragment = new NavModeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResort = (Resort) getArguments().getSerializable(ARG_RESORT);
        mSkiRoute = (SkiRoute) getArguments().getSerializable(ARG_ROUTE);
        mSkiersLocation = new SkiersLocation(mResort);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_mode, container, false);
        mRecyclerView = view.findViewById(R.id.rv_nav_mode);

        // Create adapter passing in the sample user data
        EdgeAdapter adapter = new EdgeAdapter(mSkiRoute);
        // Attach the adapter to the recycler view to populate items
        mRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
