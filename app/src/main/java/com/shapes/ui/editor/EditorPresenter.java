package com.shapes.ui.editor;

import android.graphics.Color;

import com.shapes.data.Shape;

import java.util.Random;
import java.util.Stack;

import javax.inject.Inject;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class EditorPresenter implements EditorContract.Presenter {

    /**
     * Canvas width and height in pixels
     */
    private int canvasWidth, canvasHeight;


    /**
     * Size of shapes in pixels
     */
    private int shapeSizeInPx;


    /**
     * Ref. to the view
     */
    private EditorContract.View view;


    /**
     * All shapes currently shown on canvas
     */
    private Stack<Shape> shapesDrawnOnCanvas;


    @Inject
    public EditorPresenter() {
        this.shapesDrawnOnCanvas = new Stack<>();
    }


    @Override
    public void init(int canvasWidth, int canvasHeight, int shapeSizeInPx) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.shapeSizeInPx = shapeSizeInPx;
    }


    @Override
    public void generateShape(int type) {

        // create new shape
        Shape shape = new Shape(shapesDrawnOnCanvas.size() + 1, type);

        // set shape size
        shape.setHeight(shapeSizeInPx);
        shape.setWidth(shapeSizeInPx);

        // set shape random color
        shape.setColor(generateRandomColor());

        // TODO: 28/10/2018 set random shape positioning on canvas

        // show on screen
        int index = view.drawShape(shape);

        // set index on shape
        shape.setViewIndex(index);

        // add shape in stack
        shapesDrawnOnCanvas.push(shape);
    }


    @Override
    public void undoAction() {
        if (shapesDrawnOnCanvas.size() == 0) return;
        view.removeShape(shapesDrawnOnCanvas.pop().getViewIndex());
    }


    @Override
    public void setView(EditorContract.View view) {
        this.view = view;
    }


    @Override
    public void dropView() {
        view = null;
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
}
