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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private Resort mResort;
    private Graph mBasicResortGraph;
    private String mSkiAbility;

    private Button mGoButton;
    private Button mBathroomButton;
    private Button mFoodButton;
//    private EditText mToInput;
    private Switch mTopBottomSwitch;
    private Switch mRunLiftSwitch;
    private Spinner mToSpinner;
    private EditText mFromInput;
    private TextView mRoute;
    private ListView mRouteListView;
    private Button mGetLocButton;
    private TextView mLocTextView;

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
        mSkiAbility = getArguments().getString(ARG_SKI_ABILITY);
        mBasicResortGraph = new Graph(mResort.getNodes(), mResort.getEdges(), mSkiAbility);
        mSkiersLocation = new SkiersLocation(mResort);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directions, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mRouteListView = view.findViewById(R.id.list_view);

        mBathroomButton = view.findViewById(R.id.bathroom);
        mBathroomButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Find shortest route to a bathroom

                // Check if anything was inputted
                if (mFromInput.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "No start inputted!", Toast.LENGTH_LONG).show();
                } else {
                    final Node start = mResort.getNodes().get(Integer.parseInt(mFromInput.getText().toString()) - 1);
                    FacilityFinder facilityFinder = new FacilityFinder(mResort, mSkiAbility);
                    Path path = facilityFinder.pathToNearestFacility(start, "Restrooms");

                    if (path == null) {
                        mRoute.setText("This route is not possible");
                    } else {
                        if (path.getDistance() == 1) {
                            mRoute.setText("You are already here!");
                        } else {
                            SkiRoute skiRoute = new SkiRoute(path, mBasicResortGraph);
                            mRoute.setText("");
//                        mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), skiRoute));

                            // Start Nav Mode
                            Intent intent = NavModeActivity.newIntent(getActivity(), skiRoute, new SkiLevel(mSkiAbility));
                            startActivity(intent);
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

                // Check if anything was inputted
                if (mFromInput.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "No start inputted!", Toast.LENGTH_LONG).show();
                } else {

                    final Node start = mResort.getNodes().get(Integer.parseInt(mFromInput.getText().toString()) - 1);
                    FacilityFinder facilityFinder = new FacilityFinder(mResort, mSkiAbility);
                    Path path = facilityFinder.pathToNearestFacility(start, "Restaurant");

                    if (path.getDistance() == 0) {
                        mRoute.setText("This route is not possible");
                    } else {
                        if (path.getDistance() == 1) {
                            mRoute.setText("You are already here!");
                        } else {
                            SkiRoute skiRoute = new SkiRoute(path, mBasicResortGraph);
                            mRoute.setText("");
//                        mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), skiRoute));

                            // Start Nav Mode
                            Intent intent = NavModeActivity.newIntent(getActivity(), skiRoute, new SkiLevel(mSkiAbility));
                            startActivity(intent);
                        }
                    }
                }
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

//        mToInput = view.findViewById(R.id.where_to);
        mFromInput = view.findViewById(R.id.where_from);
        mFromInput.setText("Current Location");
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
                // temporarily taking node index as input
                // TODO
                final Node end;
                if (mTopBottomSwitch.isChecked()) {
                    end = ((Edge) mToSpinner.getSelectedItem()).getStart();
                } else {
                    end = ((Edge) mToSpinner.getSelectedItem()).getEnd();
                }

                // Get start node
                // Check if input is empty
                if (mFromInput.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "No start inputted!", Toast.LENGTH_LONG).show();
                } else {
                    // Current location
                    if (mFromInput.getText().toString().equals("Current Location")) {
                        Node currentNode = mSkiersLocation.getNode();
                        if (currentNode != null) {
                            if (mHarderRouteCheckBox.isChecked() || mGroomersRouteCheckBox.isChecked()) {
                                new modifyGraphRoute().execute(currentNode, end, mLongerRouteCheckBox.isChecked(), mHarderRouteCheckBox.isChecked(), mGroomersRouteCheckBox.isChecked());
                            } else {
                                new calculateRoute(getActivity(), 3000, TimeUnit.MILLISECONDS).execute(currentNode, end, mLongerRouteCheckBox.isChecked(), mBasicResortGraph);
                            }
                        } else {
                            mLocTextView.setText("You are not currently at a node");
                        }

                    // Use inputted location
                    } else {
                        final Node start = mResort.getNodes().get(Integer.parseInt(mFromInput.getText().toString()) - 1);
                        if (mHarderRouteCheckBox.isChecked() || mGroomersRouteCheckBox.isChecked()) {
                            new modifyGraphRoute().execute(start, end, mLongerRouteCheckBox.isChecked(), mHarderRouteCheckBox.isChecked(), mGroomersRouteCheckBox.isChecked());
                        } else {
                            new calculateRoute(getActivity(), 3000, TimeUnit.MILLISECONDS).execute(start, end, mLongerRouteCheckBox.isChecked(), mBasicResortGraph);
                        }
                    }
                }
            }
        });

        mLocTextView = view.findViewById(R.id.location);

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
                List<Path> paths = yen.YenKSP(graph, (Node) objects[0], (Node) objects[1], 3);
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
                mRoute.setText("This route is not possible");
                mGoButton.setEnabled(true);
                mGoButton.setText("GO");
                // TODO clear mRouteListView
            } else {
                SkiRoute skiRoute = new SkiRoute(path, mBasicResortGraph);

//                mRoute.setText("");
//                mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), skiRoute));

                // Start Nav Mode
                Intent intent = NavModeActivity.newIntent(getActivity(), skiRoute, new SkiLevel(mSkiAbility));
                startActivity(intent);
            }
        }
    }

}
