package com.shapes.ui.stats;

import com.shapes.ui.base.BasePresenter;
import com.shapes.ui.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by xnorcode on 30/10/2018.
 */
public interface StatsContract {

    interface View extends BaseView<Presenter> {


        /**
         * Displays stats on UI
         *
         * @param groupedShapes collection of number of shapes grouped by type
         */
        void showCounts(List<Map.Entry<Integer, Integer>> groupedShapes);

    }

    interface Presenter extends BasePresenter<View> {


        /**
         * gets data and calculates stats
         */
        void calculateStats();

    }
}
