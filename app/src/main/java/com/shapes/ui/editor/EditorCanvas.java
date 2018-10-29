package com.shapes.ui.editor;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by xnorcode on 28/10/2018.
 */
public class EditorCanvas extends FrameLayout implements Canvas {


    /**
     * The size for each grid and the total number of grids
     */
    private int gridSize, gridsCapacity;


    /**
     * The number of columns and rows
     */
    private int columns, rows;


    /**
     * The padding for width and height
     */
    private int widthPadding, heightPadding;


    public EditorCanvas(Context context) {
        super(context);
    }

    public EditorCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditorCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void calculateGrids(int shapeSize) {

        // set size of each grid
        this.gridSize = shapeSize;

        // get canvas dimensions
        int w = getWidth();
        int h = getHeight();

        // calc columns and rows
        columns = w / shapeSize;
        rows = h / shapeSize;

        // calc total num of grids
        gridsCapacity = columns * rows;

        // calc padding
        widthPadding = (w - (columns * shapeSize)) / 2;
        heightPadding = (h - (rows * shapeSize)) / 2;
    }


    @Override
    public int getGridCapacity() {
        return gridsCapacity;
    }


    @Override
    public int getGridSize() {
        return gridSize;
    }


    @Override
    public int getPositionXForGrid(int grid) {
        return ((grid - 1) % columns) * gridSize + widthPadding;
    }


    @Override
    public int getPositionYForGrid(int grid) {
        return ((grid - 1) / columns) * gridSize + heightPadding;
    }
}
