package com.shapes.ui.editor;

import android.graphics.Color;

import com.shapes.data.Shape;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
     * All shapes currently shown on canvas
     */
    private Stack<Shape> shapesDrawnOnCanvas;


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
        this.shapesDrawnOnCanvas = new Stack<>();
        this.compositeDisposable = new CompositeDisposable();
        this.usedGrids = new HashSet<>();
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
            Shape shape = new Shape(shapesDrawnOnCanvas.size() + 1, type);

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

                    // add shape in stack
                    shapesDrawnOnCanvas.push(shape);
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
        if (shapesDrawnOnCanvas.size() == 0) return;
        view.removeShapeAt(shapesDrawnOnCanvas.pop().getViewIndex());
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


    // generating random color for each shape
    private int generateRandomColor() {
        Random rnd = new Random();
        int r = rnd.nextInt(200);
        int g = 136;
        int b = rnd.nextInt(220);
        // TODO: 28/10/2018 replace Color.rgb() method with java one
        return Color.rgb(r, g, b);
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
