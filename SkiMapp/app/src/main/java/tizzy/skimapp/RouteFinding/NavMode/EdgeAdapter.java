package tizzy.skimapp.RouteFinding.NavMode;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import tizzy.skimapp.R;
import tizzy.skimapp.RouteFinding.SkiRoute.SkiRoute;

public class EdgeAdapter extends RecyclerView.Adapter<EdgeAdapter.ViewHolder> {

    private SkiRoute mSkiRoute;

    public EdgeAdapter(SkiRoute skiRoute) {
        mSkiRoute = skiRoute;
    }

    @Override
    public EdgeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        //View contactView = inflater.inflate(R.layout.item_edge, parent, false);
        View contactView = inflater.inflate(R.layout.list_item_route, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EdgeAdapter.ViewHolder holder, int position) {

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        ImageView iconView = holder.iconView;

        if (mSkiRoute.getInfoEncoding(position) > 0) {
            iconView.setImageResource(R.drawable.ic_ski);
            textView.setText("Ski down " + mSkiRoute.getEdgeName(position));
            iconView.setVisibility(View.VISIBLE);
        } else {
            iconView.setImageResource(R.drawable.ic_lift);
            textView.setText("Ride the " + mSkiRoute.getEdgeName(position) + " " + mSkiRoute.getLiftType(position));
            iconView.setVisibility(View.VISIBLE);
        }

        if (mSkiRoute.isSegmentCompleted(position)) {
            textView.setTextColor(Color.parseColor("#87CEEB"));
            iconView.setImageResource(R.drawable.ic_check);
            iconView.setVisibility(View.VISIBLE);
        } else {
            textView.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return mSkiRoute.getNumberOfEdges();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView iconView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.edge_name);
            iconView = itemView.findViewById(R.id.edge_icon);
        }
    }
}
