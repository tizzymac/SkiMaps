package tizzy.skimapp.RouteFinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;

public class RouteViewAdapter extends BaseAdapter {

    private static Path mPath;
    private Resort mResort;
    private LayoutInflater mInflater;

    public RouteViewAdapter(Context context, Path path, Resort resort){
        this.mPath = path;
        this.mResort = resort;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mPath.getDistance() - 1; // will have to change this
    }

    @Override
    public Object getItem(int position) {
        return mPath.getNode(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // ?
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_node, null);
            holder = new ViewHolder();
            holder.edgeID = convertView.findViewById(R.id.node_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.edgeID.setText(mPath.getNode(position).getId());
        Edge edge = mPath.getEdgeFromNodes(mResort, mPath.getNode(position), mPath.getNode(position+1));
        holder.edgeID.setText(edge.getString());

        return convertView;
    }

    static class ViewHolder{
        TextView edgeID;
    }
}
