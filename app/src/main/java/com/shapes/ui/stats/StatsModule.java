package com.shapes.ui.stats;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xnorcode on 30/10/2018.
 */
@Module
public abstract class StatsModule {

    @ContributesAndroidInjector
    abstract StatsFragment providesStatsFragment();

    @Binds
    abstract StatsContract.Presenter providesPresenter(StatsPresenter presenter);

}
