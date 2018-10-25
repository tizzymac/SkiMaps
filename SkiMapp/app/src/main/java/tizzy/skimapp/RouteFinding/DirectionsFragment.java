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
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Resort;

public class DirectionsFragment extends Fragment {
    private static final String ARG_RESORT = "resort";

    private Resort mResort;
    private Graph mResortGraph;

    private Button setLevelButton;
    private EditText mToInput;
    private EditText mFromInput;
    private TextView mRoute;

    public static DirectionsFragment newInstance(Resort resort) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESORT, resort);
        DirectionsFragment fragment = new DirectionsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResort = (Resort) getArguments().getSerializable(ARG_RESORT);
        mResortGraph = new Graph(mResort.getNodes(), mResort.getEdges());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directions, container, false);

        mToInput = view.findViewById(R.id.where_to);
        mFromInput = view.findViewById(R.id.where_from);
        mRoute = view.findViewById(R.id.route);

        setLevelButton = view.findViewById(R.id.go_button);
        setLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get start and end nodes
                // temporarily taking node index as input
                Node start = mResort.getNodes().get(Integer.parseInt(mToInput.getText().toString()));
                Node end = mResort.getNodes().get(Integer.parseInt(mFromInput.getText().toString()));

                // Calculate route
                Dijkstra dijkstra = new Dijkstra(mResortGraph);
                dijkstra.execute(start);
                LinkedList<Node> path = dijkstra.getPath(end);

                // Display
                String route = "";
                for (Node n : path) {
                    route = route + n.getId();
                }
                mRoute.setText(route);
            }
        });

        return view;
    }
}
