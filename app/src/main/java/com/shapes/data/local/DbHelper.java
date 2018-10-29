package com.shapes.data.local;

import com.shapes.data.Shape;

import java.util.List;

/**
 * Created by xnorcode on 29/10/2018.
 */
public interface DbHelper {


    /**
     * Gets all shapes from database
     *
     * @return list with all shapes
     */
    List<Shape> getShapes();


    /**
     * Gets a shape from database
     *
     * @param id The id of the shape to get
     * @return The shape
     */
    Shape getShape(int id);


    /**
     * Inserts a shape in the database
     *
     * @param shape The shape to be inserted
     */
    void insertShape(Shape shape);


    /**
     * Updates a shape in the database
     *
     * @param shape The shape to be updated
     */
    void updateShape(Shape shape);


    /**
     * Deletes a shape from the database
     *
     * @param id The id of the shape
     */
    void deleteShape(int id);


    /**
     * Deletes all shapes in the database
     */
    void deleteShapes();
}
