package com.shapes.ui.base;

/**
 * Created by xnorcode on 27/10/2018.
 */
public interface BasePresenter<T> {


    /**
     * Binds View to Presenter
     *
     * @param view Ref. to View
     */
    void setView(T view);


    /**
     * Removes View ref.
     */
    void dropView();
}
