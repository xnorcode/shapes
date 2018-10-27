package com.shapes.ui.base;

/**
 * Created by xnorcode on 27/10/2018.
 */
public interface BaseView<T> {


    /**
     * Bind presenter to View
     *
     * @param presenter Ref. of Presenter
     */
    void setPresenter(T presenter);
}
