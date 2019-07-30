package tizzy.skimapp.RouteFinding;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;
import tizzy.skimapp.ResortModel.SkiLevel;
import tizzy.skimapp.RouteFinding.KShortestPaths.Yen;
import tizzy.skimapp.RouteFinding.NavMode.NavModeActivity;

public class DirectionsFragment extends Fragment {
    private static final String ARG_SKI_ABILITY = "ski_ability";
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private Resort mResort;
    private Graph mBasicResortGraph;
    private SkiLevel mSkiAbility;

    // Greeting
    private TextView mGreetingText;
    private TextView mLevelText;

    private Button mGoButton;
    private ImageButton mBathroomButton;
    private ImageButton mFoodButton;
    private Button mSelectDestButton;
    private Switch mTopBottomSwitch;
    private Switch mRunLiftSwitch;
    private Spinner mToSpinner;
    private TextView mRoute;
    private BottomSheetBehavior mBottomSheetBehavior;

    // route options
    private CheckBox mLongerRouteCheckBox;
    private CheckBox mHarderRouteCheckBox;
    private CheckBox mGroomersRouteCheckBox;

    private SkiersLocation mSkiersLocation;
    LocationManager locationManager;

    public static DirectionsFragment newInstance(String skiAbility) {
        Bundle args = new Bundle();
        args.putString(ARG_SKI_ABILITY, skiAbility);
        DirectionsFragment fragment = new DirectionsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResort = Resort.get(getActivity());
        mSkiAbility = new SkiLevel(getArguments().getString(ARG_SKI_ABILITY), mResort.getRegion());
        mBasicResortGraph = new Graph(mResort.getNodes(), mResort.getEdges(), mSkiAbility.getLevelNumber());
        mSkiersLocation = new SkiersLocation(mResort);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directions, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mBottomSheetBehavior = new BottomSheetBehavior().from(view.findViewById(R.id.destinationSelectionBottomSheet));
        mBottomSheetBehavior.setPeekHeight(0);

        // Greeting
        mGreetingText = view.findViewById(R.id.greeting);
        mGreetingText.setText("Welcome back, " + "name.");
        mLevelText = view.findViewById(R.id.ski_ability);
        mLevelText.setText(mSkiAbility.getLevelString());
        mLevelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Change level", Toast.LENGTH_SHORT).show();
            }
        });


        mBathroomButton = view.findViewById(R.id.bathroom);
        mBathroomButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Find shortest route to a bathroom

                // Get start node
                Node start = getStartNode();
                if (start != null) {
                    if (start != null) {
                        FacilityFinder facilityFinder = new FacilityFinder(mResort, mSkiAbility);
                        Path path = facilityFinder.pathToNearestFacility(start, "Restrooms");

                        if (path == null) {
                            //mRoute.setText("This route is not possible");
                            Toast.makeText(getActivity(), "This route is not possible", Toast.LENGTH_LONG).show();
                        } else {
                            if (path.getDistance() == 1) {
                                //mRoute.setText("You are already here!");
                                Toast.makeText(getActivity(), "You are already here!", Toast.LENGTH_LONG).show();
                            } else {
                                SkiRoute skiRoute = new SkiRoute(path, mBasicResortGraph);
                                mRoute.setText("");
//                        mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), skiRoute));

                                // Start Nav Mode
                                Intent intent = NavModeActivity.newIntent(getActivity(), skiRoute, new SkiLevel(mSkiAbility.getLevelString(), mResort.getRegion()));
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });

        mFoodButton = view.findViewById(R.id.food);
        mFoodButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Find shortest route to a restaurant

                Node start = getStartNode();
                if (start != null) {
                    FacilityFinder facilityFinder = new FacilityFinder(mResort, mSkiAbility);
                    Path path = facilityFinder.pathToNearestFacility(start, "Restaurant");

                    if (path.getDistance() == 0) {
                        //mRoute.setText("This route is not possible");
                        Toast.makeText(getActivity(), "No start inputted!", Toast.LENGTH_LONG).show();
                    } else {
                        if (path.getDistance() == 1) {
                            //mRoute.setText("You are already here!");
                            Toast.makeText(getActivity(), "You are already here!", Toast.LENGTH_LONG).show();

                        } else {
                            SkiRoute skiRoute = new SkiRoute(path, mBasicResortGraph);
                            mRoute.setText("");
//                        mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), skiRoute));

                            // Start Nav Mode
                            Intent intent = NavModeActivity.newIntent(getActivity(), skiRoute, new SkiLevel(mSkiAbility.getLevelString(), mResort.getRegion()));
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        mSelectDestButton = view.findViewById(R.id.select_dest);
        mSelectDestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Bottom sheet comes up
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        // Route options
        mLongerRouteCheckBox = view.findViewById(R.id.longer_checkbox);
        mHarderRouteCheckBox = view.findViewById(R.id.harder_checkbox);
        mGroomersRouteCheckBox = view.findViewById(R.id.groomers_checkbox);

        mTopBottomSwitch = view.findViewById(R.id.top_bottom_toggle);
        mTopBottomSwitch.setChecked(false);

        mRunLiftSwitch = view.findViewById(R.id.lift_run_toggle);
        mRunLiftSwitch.setChecked(false);
        mRunLiftSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToSpinner.setAdapter(getAdapter(mRunLiftSwitch.isChecked()));
            }
        });
        mToSpinner = view.findViewById(R.id.to_spinner);
        mToSpinner.setAdapter(getAdapter(mRunLiftSwitch.isChecked()));

        mRoute = view.findViewById(R.id.route);

        mGoButton = view.findViewById(R.id.go_button);
        mGoButton.setEnabled(true);
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable go button
                mGoButton.setText("...");
                mGoButton.setEnabled(false);

                // Get start and end nodes
                // taking node index as input
                final Node end;
                if (mTopBottomSwitch.isChecked()) {
                    end = ((Edge) mToSpinner.getSelectedItem()).getStart();
                } else {
                    end = ((Edge) mToSpinner.getSelectedItem()).getEnd();
                }

                // Get start node
                Node start = getStartNode();
                if (start != null) {
                    if (mHarderRouteCheckBox.isChecked() || mGroomersRouteCheckBox.isChecked()) {
                        new modifyGraphRoute().execute(start, end, mLongerRouteCheckBox.isChecked(), mHarderRouteCheckBox.isChecked(), mGroomersRouteCheckBox.isChecked());
                    } else {
                        new calculateRoute(getActivity(), 3000, TimeUnit.MILLISECONDS).execute(start, end, mLongerRouteCheckBox.isChecked(), mBasicResortGraph);
                    }
                }
            }
        });

        return view;
    }

    private LocationListener myLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            mSkiersLocation.updateLocation(getActivity(), location);
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
            // Request Permissions
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);

        // Reset toggles
        mTopBottomSwitch.setChecked(false);
        mRunLiftSwitch.setChecked(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(myLocationListener);
    }

    private BaseAdapter getAdapter(Boolean run) {

        if (run) {
            ArrayAdapter<Run> adapter = new ArrayAdapter<>(
                    getActivity().getApplicationContext(),
                    R.layout.spinner_item,
                    mResort.getRuns());
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            return adapter;
        } else {
            ArrayAdapter<Lift> adapter = new ArrayAdapter<>(
                    getActivity().getApplicationContext(),
                    R.layout.spinner_item,
                    mResort.getLifts());
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            return adapter;
        }
    }

    private class modifyGraphRoute extends AsyncTask<Object, Integer, Graph> {

        Node start;
        Node end;
        Boolean longer;

        @Override
        protected Graph doInBackground(Object... objects) {
            start = (Node) objects[0];
            end = (Node) objects[1];
            longer = (Boolean) objects[2];
            Boolean harder = (boolean) objects[3];
            Boolean onlyGroomers = (boolean) objects[4];

            // If harder and/or only groomers selected, we need to build a new resort graph
            Graph modifiedGraph = mBasicResortGraph;
            if (harder) {
                modifiedGraph.harderRunsPrefered();
            }
            if (onlyGroomers) {
                modifiedGraph.groomersOnly();
            }

            return modifiedGraph;
        }

        @Override
        protected void onPostExecute(Graph graph) {
            // now calculate route
            new calculateRoute(getActivity(), 3000, TimeUnit.MILLISECONDS).execute(start, end, longer, graph);
        }
    }

    private class calculateRoute extends AsyncTaskWithTimeout<Object, Integer, Path> {

        public calculateRoute(Activity context, long timeout, TimeUnit units) {
            super(context, timeout, units);
        }

        @Override
        protected Path runInBackground(Object... objects) {
            Path path;
            Boolean longer = (boolean) objects[2];
            Graph graph = (Graph) objects[3];

            // If longer route is checked
            if (longer) {
                Yen yen = new Yen(mResort);
                List<Path> paths = yen.YenKSP(graph, (Node) objects[0], (Node) objects[1], 4);
                path = paths.get(paths.size() - 1);

                // Shortest route
            } else {
                Dijkstra dijkstra = new Dijkstra(graph);
                dijkstra.execute((Node) objects[0]);       // start node
                path = dijkstra.getPath((Node) objects[1]);    // end node

            }
            return path;
        }

        @Override
        protected void onPostExecute(Path path) {
            if (path == null) {
                //mRoute.setText("This route is not possible");
                Toast.makeText(getActivity(), "This route is not possible", Toast.LENGTH_LONG).show();
                mGoButton.setEnabled(true);
                mGoButton.setText("GO");
            } else {
                SkiRoute skiRoute = new SkiRoute(path, mBasicResortGraph);

                mRoute.setText("");
//                mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), skiRoute));

                // Start Nav Mode
                Intent intent = NavModeActivity.newIntent(getActivity(), skiRoute, new SkiLevel(mSkiAbility.getLevelString(), mResort.getRegion()));
                startActivity(intent);
            }
        }
    }

    private Node getStartNode() {
        Node currentNode = mSkiersLocation.getNode();
        if (currentNode != null) {
            // Return current location node
            return currentNode;
        } else {
            Toast.makeText(getActivity(), "Unable to locate you.", Toast.LENGTH_LONG).show();
            mGoButton.setText("GO");
            mGoButton.setEnabled(true);
            return null;
        }
    }

}
