package tizzy.skimapp.RouteFinding;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;

public class DirectionsFragment extends Fragment {
    private static final String ARG_RESORT = "resort";
    private static final String ARG_SKI_ABILITY = "ski_ability";

    private Resort mResort;
    private Graph mResortGraph;
    private String mSkiAbility;

    private Button mGoButton;
    private Button mBathroomButton;
    private Button mFoodButton;
    private EditText mToInput;
    private EditText mFromInput;
    private TextView mRoute;
    private ListView mRouteListView;

    private SkiersLocation mCurrentLocation;

    public static DirectionsFragment newInstance(Resort resort, String skiAbility) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESORT, resort);
        args.putString(ARG_SKI_ABILITY, skiAbility);
        DirectionsFragment fragment = new DirectionsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResort = (Resort) getArguments().getSerializable(ARG_RESORT);
        mSkiAbility = getArguments().getString(ARG_SKI_ABILITY);
        mResortGraph = new Graph(mResort.getNodes(), mResort.getEdges(), mSkiAbility);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directions, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mRouteListView = view.findViewById(R.id.list_view);

        mBathroomButton = view.findViewById(R.id.bathroom);
        mBathroomButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Find shortest route to a bathroom

                // Pretend current node is 2
                FacilityFinder facilityFinder = new FacilityFinder(mResort, mSkiAbility);
                Path path = facilityFinder.pathToNearestFacility(mResort.getNodes().get(1), "Restrooms");

                if (path == null) {
                    mRoute.setText("This route is not possible with your constraints.");
                } else {
                    if (path.getDistance() == 1) {
                        mRoute.setText("You are already here!");
                    } else {
                        mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), path, mResort));

                    }
                }
            }
        });

        mFoodButton = view.findViewById(R.id.food);
        mFoodButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Find shorest route to a restaurant

                // Pretend current node is 2
                FacilityFinder facilityFinder = new FacilityFinder(mResort, mSkiAbility);
                Path path = facilityFinder.pathToNearestFacility(mResort.getNodes().get(1), "Restaurant");

                if (path.getDistance() == 0) {
                    mRoute.setText("This route is not possible with your constraints.");
                } else {
                    if (path.getDistance() == 1) {
                        mRoute.setText("You are already here!");
                    } else {
                        mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), path, mResort));
                    }
                }
            }
        });

        mToInput = view.findViewById(R.id.where_to);
        mFromInput = view.findViewById(R.id.where_from);
        mRoute = view.findViewById(R.id.route);

        mGoButton = view.findViewById(R.id.go_button);
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get start and end nodes
                // temporarily taking node index as input
                Node start = mResort.getNodes().get(Integer.parseInt(mToInput.getText().toString()));
                Node end = mResort.getNodes().get(Integer.parseInt(mFromInput.getText().toString()));

                // Calculate route
                Dijkstra dijkstra = new Dijkstra(mResortGraph);
                dijkstra.execute(start);
                Path path = dijkstra.getPath(end);

                // TODO Display route in a list view
                if (path == null) {
                    mRoute.setText("This route is not possible with your constraints.");
                } else {
                    mRouteListView.setAdapter(new RouteViewAdapter(getActivity(), path, mResort));
                }
            }
        });

        return view;
    }




}
