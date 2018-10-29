package com.shapes.ui.editor;

import android.view.View;

/**
 * Created by xnorcode on 29/10/2018.
 */
public interface ShapeView {


    /**
     * Sets the paint color for the shape
     *
     * @param color The rgb color
     */
    void setColor(int color);


    /**
     * Sets the view layout params
     *
     * @param size The size of the shape
     */
    void setSize(int size);


    /**
     * Sets the visual X and Y positions of the view
     *
     * @param x The X-axis in pixels
     * @param y The Y-axis in pixels
     */
    void positionOnCanvasAt(int x, int y);


    /**
     * Sets onClick listener to the view
     *
     * @param listener The OnClickListener
     */
    void onClick(View.OnClickListener listener);


    /**
     * Sets longClick listener to the view
     *
     * @param listener The OnLongClickListener
     */
    void onLongClick(View.OnLongClickListener listener);


    /**
     * Gets a reference to the shapes view
     *
     * @return View reference
     */
    View getView();
}
