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

    }


    interface Presenter extends BasePresenter<View> {


        /**
         * Initialize Editor Screen
         *
         * @param canvaWidth  The width of the canvas
         * @param canvaHeight The height of the canvas
         */
        void init(int canvaWidth, int canvaHeight);


        /**
         * Generate a new shape
         *
         * @param type The type of the new shape
         */
        void generateShape(@SHAPE_TYPE int type);

    }
}
