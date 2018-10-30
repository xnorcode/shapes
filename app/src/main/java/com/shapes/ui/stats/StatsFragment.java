package com.shapes.ui.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shapes.R;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by xnorcode on 29/10/2018.
 */
public class StatsFragment extends DaggerFragment implements StatsContract.View {


    @BindView(R.id.statsRecyclerView)
    RecyclerView recyclerView;


    @Inject
    StatsRecyclerViewAdapter adapter;


    @Inject
    StatsPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        ButterKnife.bind(this, rootView);

        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // bind adapter to recycler view
        recyclerView.setAdapter(adapter);

        // provide presenter ref to adapter
        adapter.setPresenter(presenter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // bind view to presenter
        presenter.setView(this);

        // initialize presenter
        presenter.calculateStats();
    }

    @Override
    public void onStop() {
        super.onStop();

        // unbind view
        presenter.dropView();
    }

    @Override
    public void showCounts(List<Map.Entry<String, String>> groupedShapes) {
        adapter.swapData(groupedShapes);
    }

    @Override
    public void setPresenter(StatsContract.Presenter presenter) {
        // used for manual injection of presenter
    }
}
