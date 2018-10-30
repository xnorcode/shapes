package com.shapes.ui.stats;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shapes.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xnorcode on 30/10/2018.
 */
public class StatsRecyclerViewAdapter extends RecyclerView.Adapter<StatsRecyclerViewAdapter.StatsRecyclerViewHolder> {


    /**
     * Recycler view item view holder
     */
    class StatsRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.stats_item_caption)
        TextView caption;

        @BindView(R.id.stats_item_count)
        TextView count;

        public StatsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


    private List<Map.Entry<String, Integer>> groupedShapes;


    private StatsContract.View view;


    /**
     * Constructor
     *
     * @param view Ref tot he view data will be displayed
     */
    public StatsRecyclerViewAdapter(StatsContract.View view) {
        this.view = view;
    }


    /**
     * add new data to adapter
     *
     * @param data New data to be shown in list
     */
    public void swapData(List<Map.Entry<String, Integer>> data) {
        groupedShapes = data;
        notifyDataSetChanged();
    }


    /**
     * Remove refs
     */
    public void destroy() {
        view = null;
        if (groupedShapes != null) groupedShapes.clear();
        groupedShapes = null;
    }


    @NonNull
    @Override
    public StatsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stats_item_list, viewGroup, false);
        return new StatsRecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StatsRecyclerViewHolder statsRecyclerViewHolder, int i) {
        // null check
        if (groupedShapes == null || groupedShapes.size() == 0) return;

        // set caption
        statsRecyclerViewHolder.caption.setText(groupedShapes.get(i).getKey());

        // set count
        statsRecyclerViewHolder.count.setText(String.valueOf(groupedShapes.get(i).getValue()));
    }


    @Override
    public int getItemCount() {
        if (groupedShapes == null) return 0;
        return groupedShapes.size();
    }
}
