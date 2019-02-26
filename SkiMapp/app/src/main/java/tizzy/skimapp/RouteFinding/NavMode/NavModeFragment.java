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
import android.widget.TextView;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.SkiLevel;
import tizzy.skimapp.RouteFinding.DirectionsActivity;
import tizzy.skimapp.RouteFinding.SkiRoute;
import tizzy.skimapp.RouteFinding.SkiersLocation;

public class NavModeFragment extends Fragment {
    private static final String ARG_RESORT = "resort";
    private static final String ARG_ROUTE = "route";
    private static final String ARG_LEVEL = "skiLevel";

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private Resort mResort;
    private SkiersLocation mSkiersLocation;
    LocationManager locationManager;
    private SkiRoute mSkiRoute;
    private SkiLevel mSkiLevel;

    private RecyclerView mRecyclerView;
    private TextView mCurrentLocationTextView;
    private Button mEndRouteButton;

    public static NavModeFragment newInstance(Resort resort, SkiRoute route, SkiLevel skiLevel) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESORT, resort);
        args.putSerializable(ARG_ROUTE, route);
        args.putSerializable(ARG_LEVEL, skiLevel);
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
        mSkiLevel = (SkiLevel) getArguments().getSerializable(ARG_LEVEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_mode, container, false);
        mRecyclerView = view.findViewById(R.id.rv_nav_mode);

        // Skier's current location
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mCurrentLocationTextView = view.findViewById(R.id.current_location);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            System.out.println("Permission not granted");
            requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
        } else {
            // Permission has already been granted
            if (mSkiersLocation.isNull()) {
                // TODO
                mCurrentLocationTextView.setText("Cannot locate skier");
            } else {
                Node currentNode = mSkiersLocation.getNode();
                if (currentNode != null) {
                    //mLocTextView.setText(currentNode.getId());
                    mCurrentLocationTextView.setText(currentNode.getId());
                } else {
                    mCurrentLocationTextView.setText("You are not currently at a node");
                }
            }
        }

        // Create adapter passing in the sample user data
        EdgeAdapter adapter = new EdgeAdapter(mSkiRoute);
        // Attach the adapter to the recycler view to populate items
        mRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // TODO Test changing color of first run

        mEndRouteButton = view.findViewById(R.id.end_route);
        mEndRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to directions fragment
                Intent intent = DirectionsActivity.newIntent(getActivity(), mResort, mSkiLevel.getLevelString());
                startActivity(intent);
            }
        });

        return view;
    }

    private LocationListener myLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            mSkiersLocation.updateLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
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

}
