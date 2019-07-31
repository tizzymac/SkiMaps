package tizzy.skimapp.RouteFinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Resort;

public class RouteViewAdapter extends BaseAdapter {

    private static SkiRoute mSkiRoute;
    private LayoutInflater mInflater;

    public RouteViewAdapter(Context context, SkiRoute skiRoute){
        this.mSkiRoute = skiRoute;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mSkiRoute.getNumberOfEdges();
    }

    @Override
    public Object getItem(int position) {
        return mSkiRoute.getEdgeName(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // ?
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_route, null);
            holder = new ViewHolder();
            holder.edgeID = convertView.findViewById(R.id.edge_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.edgeID.setText(mSkiRoute.getEdgeName(position));

        return convertView;
    }

    static class ViewHolder{
        TextView edgeID;
    }


}
