package tizzy.skimapp.RouteFinding.NavMode;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tizzy.skimapp.R;
import tizzy.skimapp.RouteFinding.SkiRoute;

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
        View contactView = inflater.inflate(R.layout.item_edge, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EdgeAdapter.ViewHolder holder, int position) {

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(mSkiRoute.getEdgeName(position));

        if (mSkiRoute.isSegmentCompleted(position)) {
            textView.setTextColor(Color.GRAY);
        } else {
            textView.setTextColor(Color.WHITE);
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

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.edge_name);
        }
    }
}
