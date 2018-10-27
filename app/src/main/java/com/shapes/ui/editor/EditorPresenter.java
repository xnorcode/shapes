package com.shapes.ui.editor;

import javax.inject.Inject;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class EditorPresenter implements EditorContract.Presenter {

    /**
     * Canvas width and height in pixels
     */
    private int canvaWidth, canvaHeight;

    private int shapeRadius;

    private EditorContract.View view;

    @Inject
    public EditorPresenter() {
    }

    @Override
    public void init(int canvaWidth, int canvaHeight) {
        this.canvaWidth = canvaWidth;
        this.canvaHeight = canvaHeight;
    }

    @Override
    public void generateShape(int type) {

    }

    @Override
    public void setView(EditorContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
