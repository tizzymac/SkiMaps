package tizzy.skimapp.RouteFinding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Facility;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

public class DirectionsFragment extends Fragment {
    private static final String ARG_RESORT = "resort";
    private static final String ARG_SKI_ABILITY = "ski_ability";

    private Resort mResort;
    private Graph mResortGraph;
    private String mSkiAbility;

    private Button mGoButton;
    private Button mBathroomButton;
    private EditText mToInput;
    private EditText mFromInput;
    private TextView mRoute;

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
                    mRoute.setText(path.getRunsAndLifts(mResort, mSkiAbility));
                }
            }
        });

        mBathroomButton = view.findViewById(R.id.bathroom);
        mBathroomButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Find shortest route to a bathroom

                // Pretend current node is 1
                FacilityFinder facilityFinder = new FacilityFinder(mResort, mSkiAbility);
                Path path = facilityFinder.pathToNearestBathroom(mResort.getNodes().get(1));

                if (path == null) {
                    mRoute.setText("This route is not possible with your constraints.");
                } else {
                    mRoute.setText(path.getRunsAndLifts(mResort, mSkiAbility));
                }
            }
        });

        return view;
    }




}
