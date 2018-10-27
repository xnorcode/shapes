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
         * @param viewIndex
         */
        void removeShape(int viewIndex);

    }


    interface Presenter extends BasePresenter<View> {


        /**
         * Initialize Editor Screen
         *
         * @param canvasWidth   The width of the canvas
         * @param canvasHeight  The height of the canvas
         * @param shapeSizeInPx The size of each shape in px
         */
        void init(int canvasWidth, int canvasHeight, int shapeSizeInPx);


        /**
         * Generate a new shape
         *
         * @param type The type of the new shape
         */
        void generateShape(@SHAPE_TYPE int type);


        /**
         * Undo last action
         */
        void undoAction();

    }
}
