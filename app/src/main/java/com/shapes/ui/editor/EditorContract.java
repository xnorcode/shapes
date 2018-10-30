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
         * @return The index of the shape's view on canvas
         */
        int drawShape(Shape shape);


        /**
         * Remove a shape from canvas
         *
         * @param id The id of the shape
         */
        void removeShape(int id);


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
         * Delete a shape
         *
         * @param id The shape's id
         */
        void deleteShape(int id);


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

    }
}
