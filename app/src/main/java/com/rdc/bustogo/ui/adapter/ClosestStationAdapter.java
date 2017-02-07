package com.rdc.bustogo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.bustogo.R;

import java.util.List;

public class ClosestStationAdapter extends RecyclerView.Adapter<ClosestStationAdapter.ViewHolder> implements View.OnClickListener {
    private StationListener stationListener = null;
    private List<String> stations;
    private List<String> distance;
    private List<String> detail;

    public void setData(List<String> stations, List<String> distance, List<String> detail) {
        this.stations = stations;
        this.distance = distance;
        this.detail = detail;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_closest_station_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvStation.setText(stations.get(position));
        holder.tvDistance.setText("  " + distance.get(position) + "m");
        holder.tvDetail.setText("详情：" + detail.get(position));
        holder.rlStation.setTag(position);
        holder.rlStation.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return distance.size();
    }

    @Override
    public void onClick(View v) {
        if (stationListener != null) {
            stationListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface StationListener {
        void onItemClick(View view, int tag);
    }

    public void setOnItemClickListener(StationListener listener) {
        stationListener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStation;
        TextView tvDistance;
        TextView tvDetail;
        RelativeLayout rlStation;

        ViewHolder(View itemView) {
            super(itemView);
            tvStation = (TextView) itemView.findViewById(R.id.tv_station);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            tvDetail = (TextView) itemView.findViewById(R.id.expandable_text);
            rlStation = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
        }
    }
}
