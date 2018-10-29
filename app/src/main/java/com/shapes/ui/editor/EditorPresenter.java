package com.shapes.ui.editor;

import com.shapes.data.Shape;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.shapes.utils.Constants.EDITOR_ADD_SHAPE;
import static com.shapes.utils.Constants.EDITOR_SWAP_SHAPE;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class EditorPresenter implements EditorContract.Presenter {


    /**
     * The grid capacity
     */
    private int grids;


    /**
     * Ref. to the view
     */
    private EditorContract.View view;


    /**
     * Cache of all shapes currently shown on canvas
     */
    private Map<Integer, Shape> shapesCache;


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


    @Inject
    public EditorPresenter() {
        this.compositeDisposable = new CompositeDisposable();
        this.usedGrids = new HashSet<>();
        this.editorActionsTaken = new Stack<>();
        this.shapesCache = new HashMap<>();
    }


    @Override
    public void init(int grids) {
        this.grids = grids;
    }


    @Override
    public void generateShape(int type) {
        compositeDisposable.clear();
        compositeDisposable.add(Single.<Shape>create(emitter -> {

            // find a random slot on canvas grid
            int gridIndex = getRandomGrid();

            // if grid full return
            if (gridIndex == -1) {
                emitter.onError(new Exception("Editor is full!"));
                return;
            }

            // create new shape
            Shape shape = new Shape(shapesCache.size() + 1, type);

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
                    // show on screen
                    int index = view.drawShape(shape);

                    // usedGrids index on shape
                    shape.setViewIndex(index);

                    // save editor action in stack
                    editorActionsTaken.push(new AbstractMap.SimpleEntry<>(EDITOR_ADD_SHAPE, shape.getId()));

                    // save shape in cache
                    shapesCache.put(shape.getId(), shape);

                }, throwable -> view.showNotification(throwable.getMessage())));
    }


    @Override
    public void replaceShapeWith(int viewIndex, int type, int grid) {

        // remove old shape

        // generate new shape

        // usedGrids position of new shape
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
                // get last added shape
                Shape shape = shapesCache.get(shapesCache.size());

                // remove view from canvas
                view.removeShapeAt(shape.getViewIndex());

                // delete from cache
                shapesCache.remove(shape.getId());
                break;

            case EDITOR_SWAP_SHAPE:
                break;
        }
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

        // TODO: 28/10/2018 exclude slots used
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
}
