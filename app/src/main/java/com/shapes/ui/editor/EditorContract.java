package com.shapes.ui.editor;

import com.shapes.data.SHAPE_TYPE;
import com.shapes.data.Shape;
import com.shapes.ui.base.BasePresenter;
import com.shapes.ui.base.BaseView;

/**
 * Created by xnorcode on 27/10/2018.
 */
public interface EditorContract {


    interface View extends BaseView<Presenter> {


        /**
         * Draw the new shape on canvas
         *
         * @param shape The new Shape object to draw
         */
        void drawShape(Shape shape);


        /**
         * Remove a shape from canvas
         *
         * @param id The id of the shape
         */
        void removeShape(int id);


        /**
         * remove all shapes from canvas
         */
        void removeAllShapes();


        /**
         * Display user notification
         *
         * @param msg The message to display
         */
        void showNotification(String msg);

    }


    interface Presenter extends BasePresenter<View> {


        /**
         * Initialize Editor Screen
         *
         * @param grids The grid capacity
         */
        void init(int grids);


        /**
         * Generate a new shape
         *
         * @param type The type of the new shape
         */
        void generateShape(@SHAPE_TYPE int type);


        /**
         * Add shape to view
         *
         * @param shape The shape to add
         */
        void addShape(Shape shape);


        /**
         * Delete a shape
         *
         * @param id         The shape's id
         * @param userAction Enable if triggered by user
         */
        void deleteShape(int id, boolean userAction);


        /**
         * Swap shape type
         *
         * @param id     Current shape id
         * @param revert Enable to revert shape type
         */
        void swapShape(int id, boolean revert);


        /**
         * Undo last action
         */
        void undoAction();


        /**
         * Save all editor actions
         */
        void persistActions();


        /**
         * Clears all shapes from editor
         */
        void clearEditor();

    }
}
