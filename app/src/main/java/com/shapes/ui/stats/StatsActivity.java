package com.shapes.ui.stats;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shapes.R;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by xnorcode on 29/10/2018.
 */
public class StatsActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // remove activity's background theme
        getWindow().setBackgroundDrawable(null);

        // Add fragment to activity
        StatsFragment fragment = (StatsFragment) getSupportFragmentManager().findFragmentById(R.id.stats_fragment_container);
        if (fragment == null) {
            fragment = new StatsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stats_fragment_container, fragment)
                    .commit();
        }
    }
}
