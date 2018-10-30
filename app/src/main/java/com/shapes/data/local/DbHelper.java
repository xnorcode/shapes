package com.shapes.data.local;

import com.shapes.data.EditorAction;
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


    /**
     * Gets all editor actions from db
     *
     * @return List of actions
     */
    List<EditorAction> getActions();


    /**
     * Get specific action from db
     *
     * @param id Editor action id
     * @return EditorAction
     */
    EditorAction getAction(int id);


    /**
     * Saves a list of actions in db
     *
     * @param actions a list of actions to save
     */
    void insertActions(List<EditorAction> actions);


    /**
     * Deletes all actions in db
     */
    void deleteActions();


    /**
     * Deletes specific action in db
     *
     * @param id The id of the action to be deleted
     */
    void deleteAction(int id);
}
