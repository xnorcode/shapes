package com.shapes.ui.editor;

/**
 * Created by xnorcode on 28/10/2018.
 */
public interface Canvas {


    /**
     * Calculate canvas grid
     *
     * @param shapeSize The size for each grid
     */
    void calculateGrids(int shapeSize);


    /**
     * Get the grid capacity
     *
     * @return Number of total grids
     */
    int getGridCapacity();


    /**
     * Get the size of each grid
     *
     * @return Size of each grid
     */
    int getGridSize();


    /**
     * Get the x-axis position for specific grid
     *
     * @param grid Grid index
     * @return The x-axis of grid
     */
    int getPositionXForGrid(int grid);


    /**
     * Get the y-axis position for specific grid
     *
     * @param grid Grid index
     * @return The y-axis of grid
     */
    int getPositionYForGrid(int grid);
}
