package com.shapes.ui.stats;

import android.util.Log;

import com.shapes.data.Shape;
import com.shapes.data.ShapeDataSource;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.shapes.utils.Constants.SHAPE_NAME_CIRCLE;
import static com.shapes.utils.Constants.SHAPE_NAME_SQUARE;
import static com.shapes.utils.Constants.SHAPE_NAME_TRIANGLE;
import static com.shapes.utils.Constants.SHAPE_TYPE_CIRCLE;
import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;
import static com.shapes.utils.Constants.SHAPE_TYPE_TRIANGLE;

/**
 * Created by xnorcode on 30/10/2018.
 */
public class StatsPresenter implements StatsContract.Presenter {


    /**
     * Tag used for debugging
     */
    private static final String TAG = StatsPresenter.class.getName();


    /**
     * Ref. to the view
     */
    private StatsContract.View view;


    /**
     * Manager disposables
     */
    private CompositeDisposable compositeDisposable;


    /**
     * Shape Data Repository
     */
    private ShapeDataSource shapeRepository;


    @Inject
    public StatsPresenter(ShapeDataSource shapeRepository) {
        this.shapeRepository = shapeRepository;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void calculateStats() {
        compositeDisposable.add(shapeRepository.load()
                .flatMap(this::groupAndCalculateTotals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groupedShapes -> {
                    view.showCounts(groupedShapes);
                    Log.d(TAG, "Shapes loaded successfully");
                }, t -> Log.d(TAG, "There was an issue loading the shapes")));
    }


    @Override
    public void deleteShapesOfType(String typeName) {

        // get shape type to be deleted
        int type = getShapeTypeFromName(typeName);

        // get all ids to be deleted
        compositeDisposable.add(shapeRepository.load()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(Observable::fromIterable)
                // filter per shape type
                .filter(shape -> shape.getType() == type)
                .map(Shape::getId)
                .toList()
                // delete actions and shapes from db
                .flatMap(ids -> shapeRepository.delete(ids)
                        .flatMap(v -> shapeRepository.deleteActions(ids)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    Log.d(TAG, "Shape was removed");
                    // refresh stats screen
                    calculateStats();
                }, t -> Log.d(TAG, "Shape was not removed")));
    }


    @Override
    public void setView(StatsContract.View view) {
        this.view = view;
    }


    @Override
    public void dropView() {
        view = null;
        if (compositeDisposable != null) compositeDisposable.clear();
    }


    /**
     * Helper method to calculate shape totals by group
     *
     * @param shapes List with all shapes
     * @return Observable list of grouped shape counts
     */
    private Single<List<Map.Entry<String, String>>> groupAndCalculateTotals(List<Shape> shapes) {

        // counts
        int square = 0;
        int circle = 0;
        int triangle = 0;

        // calculate counts
        for (Shape shape : shapes) {
            switch (shape.getType()) {
                case SHAPE_TYPE_SQUARE:
                    square++;
                    break;
                case SHAPE_TYPE_CIRCLE:
                    circle++;
                    break;
                case SHAPE_TYPE_TRIANGLE:
                    triangle++;
                    break;
            }
        }

        // add counts to list
        List<Map.Entry<String, String>> result = new ArrayList<>();
        result.add(new AbstractMap.SimpleEntry<>(SHAPE_NAME_SQUARE, String.valueOf(square)));
        result.add(new AbstractMap.SimpleEntry<>(SHAPE_NAME_CIRCLE, String.valueOf(circle)));
        result.add(new AbstractMap.SimpleEntry<>(SHAPE_NAME_TRIANGLE, String.valueOf(triangle)));

        // return observable
        return Single.<List<Map.Entry<String, String>>>just(result);
    }


    /**
     * Helper method to get type from shape name
     *
     * @param type The name of the shape
     * @return Type of the shape
     */
    private int getShapeTypeFromName(String type) {
        switch (type) {
            case SHAPE_NAME_SQUARE:
                return SHAPE_TYPE_SQUARE;
            case SHAPE_NAME_CIRCLE:
                return SHAPE_TYPE_CIRCLE;
            case SHAPE_NAME_TRIANGLE:
                return SHAPE_TYPE_TRIANGLE;
            default:
                return -1;
        }
    }
}
