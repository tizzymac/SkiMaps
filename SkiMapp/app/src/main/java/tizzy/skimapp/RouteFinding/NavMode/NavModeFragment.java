package tizzy.skimapp.RouteFinding.NavMode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import tizzy.skimapp.Emergency.EmergencyActivity;
import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.SkiLevel;
import tizzy.skimapp.RouteFinding.DirectionsActivity;
import tizzy.skimapp.RouteFinding.SkiRoute;
import tizzy.skimapp.RouteFinding.SkiersLocation;

public class NavModeFragment extends Fragment {
    private static final String ARG_ROUTE = "route";
    private static final String ARG_LEVEL = "skiLevel";

    private Resort mResort;
    private SkiersLocation mSkiersLocation;
    LocationManager locationManager;
    private SkiRoute mSkiRoute;
    private SkiLevel mSkiLevel;

    private RecyclerView mRecyclerView;
    private TextView mCurrentLocationTextView;
    private Button mEndRouteButton;
    private ImageButton mEmergencyButton;

    private int mCurrentSegment;
    EdgeAdapter mAdapter;

    public static NavModeFragment newInstance(SkiRoute route, SkiLevel skiLevel) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ROUTE, route);
        args.putSerializable(ARG_LEVEL, skiLevel);
        NavModeFragment fragment = new NavModeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResort = Resort.get(getActivity());
        mSkiRoute = (SkiRoute) getArguments().getSerializable(ARG_ROUTE);
        mSkiersLocation = new SkiersLocation(mResort);
        mSkiLevel = (SkiLevel) getArguments().getSerializable(ARG_LEVEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_mode, container, false);
        mRecyclerView = view.findViewById(R.id.rv_nav_mode);

        mCurrentSegment = 0;

        // Skier's current location
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mCurrentLocationTextView = view.findViewById(R.id.current_location);
        if (mSkiersLocation.isNull()) {
            // TODO
            mCurrentLocationTextView.setText("Unable to locate skier");
        } else {
            getSkiersNode();
        }


        // Create adapter passing in the sample user data
        mAdapter = new EdgeAdapter(mSkiRoute);
        // Attach the adapter to the recycler view to populate items
        mRecyclerView.setAdapter(mAdapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mEndRouteButton = view.findViewById(R.id.end_route);
        mEndRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to directions fragment
                Intent intent = DirectionsActivity.newIntent(getActivity(), mSkiLevel.getLevelString());
                startActivity(intent);
            }
        });

        mEmergencyButton = view.findViewById(R.id.emergency_button);
        mEmergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmergencyActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        return view;
    }

    private LocationListener myLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            mSkiersLocation.updateLocation(location);
            Node currNode = getSkiersNode();

            if ((currNode != null) && (mCurrentSegment < mSkiRoute.length())) {
                // Check if current segment has been completed
                if (currNode.equals(mSkiRoute.getEndNodeAt(mCurrentSegment))) {
                    // change text color of completed segment
                    mSkiRoute.setCompleted(mCurrentSegment);
                    mCurrentSegment++;

                    // Check if route completed
                    if (mCurrentSegment == mSkiRoute.length()-1) {
                        mCurrentLocationTextView.setText("You've arrived!");
                    }

                    // refresh recycler view
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) { }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(myLocationListener);
    }

    private Node getSkiersNode() {
        Node currentNode = mSkiersLocation.getNode();
        if (currentNode != null) {
            //mLocTextView.setText(currentNode.getId());
            mCurrentLocationTextView.setText(currentNode.getId());
            return currentNode;
        } else {
            mCurrentLocationTextView.setText("You are not currently at a node");
            return null;
        }
    }

}
