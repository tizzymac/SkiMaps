package tizzy.skimapp.RouteFinding.NavMode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import tizzy.skimapp.Emergency.EmergencyActivity;
import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;
import tizzy.skimapp.ResortModel.SkiLevel;
import tizzy.skimapp.RouteFinding.DirectionsActivity;
import tizzy.skimapp.RouteFinding.SkiRoute;
import tizzy.skimapp.RouteFinding.SkiersLocation;

public class NavModeFragment extends Fragment {
    private static final String ARG_ROUTE = "route";
    private static final String ARG_LEVEL = "skiLevel";
    private static final String ARG_REGION = "region";

    private Resort mResort;
    private SkiersLocation mSkiersLocation;
    LocationManager locationManager;
    private SkiRoute mSkiRoute;
    private SkiLevel mSkiLevel;

    private RecyclerView mRecyclerView;
    private TextView mCurrentLocationTextView;
    private Button mEndRouteButton;
    private ImageButton mEmergencyButton;

    private TextToSpeech mTextToSpeech;
    private Node mLastNode;

    private int mCurrentSegment;
    EdgeAdapter mAdapter;

    public static NavModeFragment newInstance(SkiRoute route, SkiLevel skiLevelUSA) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ROUTE, route);
        args.putSerializable(ARG_LEVEL, skiLevelUSA);
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

        mTextToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result;
                    if (mSkiLevel.getRegion().equals("Europe")) {
                        result = mTextToSpeech.setLanguage(Locale.UK);
                    } else {
                        result = mTextToSpeech.setLanguage(Locale.US);
                    }
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_mode, container, false);
        mRecyclerView = view.findViewById(R.id.rv_nav_mode);

        mCurrentSegment = 0;

        // Skier's current location
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mCurrentLocationTextView = view.findViewById(R.id.current_location);
        mTextToSpeech.speak("Starting Route.", TextToSpeech.QUEUE_ADD, null);
        mTextToSpeech.speak("Take " + mSkiRoute.getEdgeName(0), TextToSpeech.QUEUE_ADD, null);

        // For debugging
//        if (mSkiersLocation.isNull()) {
//            // TODO
//            mCurrentLocationTextView.setText("Unable to locate skier");
//        } else {
//            getSkiersNode();
//        }

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
            mSkiersLocation.updateLocation(getActivity(), location);
            Node currNode = getSkiersNode();

            if ((currNode != null) && (mCurrentSegment < mSkiRoute.length())) {
                // Check if current segment has been completed
                if (currNode.equals(mSkiRoute.getEndNodeAt(mCurrentSegment))) {
                    // change text color of completed segment
                    mSkiRoute.setCompleted(mCurrentSegment);

                    // Check if route completed
                    if (mCurrentSegment == mSkiRoute.length()-1) {
                        mCurrentLocationTextView.setText("You've arrived!");
                        if (mLastNode != currNode) {
                            mTextToSpeech.speak("You have arrived!", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else {
                        // Speak next direction
                        if (mLastNode != currNode) {
                            mTextToSpeech.speak("Take " + mSkiRoute.getEdgeName(mCurrentSegment+1), TextToSpeech.QUEUE_ADD, null);
                        }
                    }

                    mCurrentSegment++;

                    // refresh recycler view
                    mAdapter.notifyDataSetChanged();
                } else {
                    // Check that node isn't midpoint of current run.
                    Run currentRun = mResort.getRun(mSkiRoute.getEdgeName(mCurrentSegment));
                    if ((currentRun != null) && (currentRun.getMidpoints() != null)) {
                        if (!currentRun.getMidpoints().contains(currNode)) {

                            // Skier has reached a different route and is therefore off course
                            mCurrentLocationTextView.setText("You've taken a wrong turn!");
                            if (mLastNode != currNode) {
                                mTextToSpeech.speak("You took a wrong turn.", TextToSpeech.QUEUE_FLUSH, null);
                            }

                            // Take the start node as their current node
                            // Take the same end node
                            // Calculate new route
                            // What about variables?

                            mEndRouteButton.setText("REROUTE");
                        }
                    }
                }
            }

            mLastNode = currNode;
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

        // For debugging
        if (currentNode != null) {
            //mLocTextView.setText(currentNode.getId());
            //mCurrentLocationTextView.setText(currentNode.getId());
            return currentNode;
        } else {
            //mCurrentLocationTextView.setText("You are not currently at a node");
            return null;
        }
    }

}
