package com.shapes.ui.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shapes.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by xnorcode on 29/10/2018.
 */
public class StatsFragment extends DaggerFragment implements StatsContract.View {

    // TODO: 29/10/2018 configure stats list screen

    @BindView(R.id.statsRecyclerView)
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void showCounts(List<Map.Entry<String, Integer>> groupedShapes) {

    }

    @Override
    public void setPresenter(StatsContract.Presenter presenter) {

    }
}
