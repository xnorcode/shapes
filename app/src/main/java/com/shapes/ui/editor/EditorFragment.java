package com.shapes.ui.editor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shapes.R;
import com.shapes.data.Shape;
import com.shapes.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class EditorFragment extends DaggerFragment implements EditorContract.View {


    @BindView(R.id.editor_fragment_canvas)
    View canvas;

    @Inject
    EditorContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editor, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // bind this view to presenter
        presenter.setView(this);
        // initialize the presenter
        presenter.init(canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) presenter.dropView();
        presenter = null;
    }

    @OnClick(R.id.btn_add_square)
    public void addSquare() {
        presenter.generateShape(Constants.SHAPE_TYPE_SQUARE);
    }

    @OnClick(R.id.btn_add_circle)
    public void addCircle() {
        presenter.generateShape(Constants.SHAPE_TYPE_CIRCLE);
    }

    @OnClick(R.id.btn_add_triangle)
    public void addTriangle() {
        presenter.generateShape(Constants.SHAPE_TYPE_TRIANGLE);
    }

    @Override
    public void drawShape(Shape shape) {

    }

    @Override
    public void setPresenter(EditorContract.Presenter presenter) {
        // used when manual injection of presenter
    }
}
