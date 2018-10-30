package com.shapes.ui.editor;

import android.util.Log;

import com.shapes.data.SHAPE_TYPE;
import com.shapes.data.Shape;
import com.shapes.data.ShapeDataSource;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.shapes.utils.Constants.EDITOR_ADD_SHAPE;
import static com.shapes.utils.Constants.EDITOR_REMOVE_SHAPE;
import static com.shapes.utils.Constants.EDITOR_SWAP_SHAPE;
import static com.shapes.utils.Constants.SHAPE_TYPE_CIRCLE;
import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;
import static com.shapes.utils.Constants.SHAPE_TYPE_TRIANGLE;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class EditorPresenter implements EditorContract.Presenter {


    /**
     * Tag used for debugging
     */
    private static final String TAG = EditorPresenter.class.getName();


    /**
     * The grid capacity
     */
    private int grids;


    /**
     * Ref. to the view
     */
    private EditorContract.View view;


    /**
     * All editor actions taken
     * <p>
     * Pair Key: Editor Action
     * Pair Value: Shape ID
     */
    private Stack<Map.Entry<Integer, Integer>> editorActionsTaken;


    /**
     * Save all used grid indexes
     */
    private Set<Integer> usedGrids;


    /**
     * Manager disposables
     */
    private CompositeDisposable compositeDisposable;


    /**
     * Shape Data Repository
     */
    private ShapeDataSource shapeRepository;


    @Inject
    public EditorPresenter(ShapeDataSource shapeRepository) {
        this.compositeDisposable = new CompositeDisposable();
        this.usedGrids = new HashSet<>();
        this.editorActionsTaken = new Stack<>();
        this.shapeRepository = shapeRepository;
    }


    @Override
    public void init(int grids) {
        this.grids = grids;

        // check if any shapes saved in db
        compositeDisposable.add(shapeRepository.load()
                .toObservable()
                .flatMap(Observable::fromIterable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shape -> view.drawShape(shape),
                        t -> view.showNotification("Blank canvas!")));
    }


    @Override
    public void generateShape(int type) {
        compositeDisposable.add(Single.<Shape>create(emitter -> {

            // find a random slot on canvas grid
            int gridIndex = getRandomGrid();

            // if grid full return
            if (gridIndex == -1) emitter.onError(new Exception("Editor is full!"));

            // create new shape
            Shape shape = new Shape(gridIndex, type);

            // set shape a random color
            shape.setColor(generateRandomColor());

            // set shape's grid index on canvas
            shape.setGridIndex(gridIndex);

            // emit shape
            emitter.onSuccess(shape);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shape -> {

                    // add shape to UI
                    addShape(shape);

                    Log.d(TAG, "A new shape was generated");
                }, t -> {
                    view.showNotification(t.getMessage());
                    Log.d(TAG, "Could not generate new shape because: " + t.getMessage());
                }));
    }

    @Override
    public void addShape(Shape shape) {

        // show on screen
        view.drawShape(shape);

        // save editor action in stack
        editorActionsTaken.push(new AbstractMap.SimpleEntry<>(EDITOR_ADD_SHAPE, shape.getId()));

        // persist in db
        compositeDisposable.add(shapeRepository.save(shape)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    Log.d(TAG, "New shape added successfully");
                }, t -> {
                    view.showNotification(t.getMessage());
                    Log.d(TAG, "A new shape was not added because: " + t.getMessage());
                }));
    }

    @Override
    public void deleteShape(int id, boolean userAction) {
        compositeDisposable.add(shapeRepository.find(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(shape -> {

                    // remove view from canvas
                    view.removeShape(shape.getId());

                    // save action if done by user
                    if (userAction) {
                        editorActionsTaken.push(new AbstractMap.SimpleEntry<>(EDITOR_REMOVE_SHAPE,
                                shape.getId()));

                        // return shape
                        return shape;
                    }

                    // deallocate grid
                    usedGrids.remove(shape.getGridIndex());

                    return shape;
                })
                .observeOn(Schedulers.io())
                .flatMap(shape -> {
                    // do not delete from db if action by user
                    if (userAction) return Single.just(true);
                    return shapeRepository.delete(shape.getId());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    Log.d(TAG, "Shape was removed successfully");
                }, t -> {
                    Log.d(TAG, "Shape was not removed because: " + t.getMessage());
                }));
    }

    @Override
    public void swapShape(int id, boolean revert) {
        compositeDisposable.add(shapeRepository.find(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(shape -> {
                    // swap shape type
                    shape.setType(swapShapeType(shape.getType(), revert));

                    // remove old shape
                    view.removeShape(shape.getId());

                    // show new shape
                    view.drawShape(shape);

                    // save editor action in stack
                    if (!revert)
                        editorActionsTaken.push(new AbstractMap.SimpleEntry<>(EDITOR_SWAP_SHAPE,
                                shape.getId()));

                    return shape;
                })
                .observeOn(Schedulers.io())
                .flatMap(shape -> shapeRepository.update(shape))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> Log.d(TAG, "Shape was updated successfully"),
                        t -> Log.d(TAG, "Shape was not updated because: " + t.getMessage())));
    }

    @Override
    public void undoAction() {

        // exit if no actions
        if (editorActionsTaken.size() == 0) return;

        // get latest action
        Map.Entry<Integer, Integer> action = editorActionsTaken.pop();

        // undo based on action type
        switch (action.getKey()) {

            case EDITOR_ADD_SHAPE:
                // delete last added shape
                deleteShape(action.getValue(), false);
                break;

            case EDITOR_SWAP_SHAPE:
                // revert type swap
                swapShape(action.getValue(), true);
                break;

            case EDITOR_REMOVE_SHAPE:
                // add shape back
                compositeDisposable.add(shapeRepository.find(action.getValue())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(shape -> {
                            // show on screen
                            view.drawShape(shape);
                        }, t -> Log.d(TAG, "There was na issue undo removal action..")));
                break;
        }
    }


    @Override
    public void clearEditor() {
        compositeDisposable.clear();
        compositeDisposable.add(shapeRepository.clear()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                            // remove all shapes from canvas
                            view.removeAllShapes();

                            // deallocate all grids
                            usedGrids.clear();

                            // clear all user actions
                            editorActionsTaken.clear();
                        },
                        t -> view.showNotification("Could not clear the canvas. Please restart app.")));
    }


    @Override
    public void setView(EditorContract.View view) {
        this.view = view;
    }


    @Override
    public void dropView() {
        view = null;
        if (compositeDisposable != null) compositeDisposable.clear();
        compositeDisposable = null;
    }


    /**
     * Helper method to select random color
     *
     * @return a random
     */
    private int generateRandomColor() {
        Random rnd = new Random();
        int r = rnd.nextInt(200);
        int g = 136;
        int b = rnd.nextInt(220);
        return 0xff000000 | (r << 16) | (g << 8) | b;
    }


    /**
     * Helper method to select random grid index
     *
     * @return Random grid index
     */
    private int getRandomGrid() {
        // get a random index
        Random random = new Random();
        int slot = random.nextInt(grids) + 1;

        // exit when all grids are full
        if (usedGrids.size() >= grids) return -1;

        // if index not used save it and return it
        if (!usedGrids.contains(slot)) {
            usedGrids.add(slot);
            return slot;
        }

        // if index is used find another
        return getRandomGrid();
    }


    /**
     * Helper method to swap shape types
     *
     * @param type   The current shape type
     * @param revert Enable to revert back the type
     * @return The new shape type
     */
    @SHAPE_TYPE
    private int swapShapeType(@SHAPE_TYPE int type, boolean revert) {
        switch (type) {

            case SHAPE_TYPE_SQUARE:
                if (revert) return SHAPE_TYPE_TRIANGLE;
                return SHAPE_TYPE_CIRCLE;

            case SHAPE_TYPE_CIRCLE:
                if (revert) return SHAPE_TYPE_SQUARE;
                return SHAPE_TYPE_TRIANGLE;

            case SHAPE_TYPE_TRIANGLE:
                if (revert) return SHAPE_TYPE_CIRCLE;
                return SHAPE_TYPE_SQUARE;

            default:
                return SHAPE_TYPE_SQUARE;
        }
    }
}
