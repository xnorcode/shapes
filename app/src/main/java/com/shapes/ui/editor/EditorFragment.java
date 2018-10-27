package com.shapes.ui.editor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shapes.R;
import com.shapes.data.Shape;
import com.shapes.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;

import static com.shapes.utils.Constants.SHAPE_TYPE_CIRCLE;
import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;
import static com.shapes.utils.Constants.SHAPE_TYPE_TRIANGLE;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class EditorFragment extends DaggerFragment implements EditorContract.View {


    @BindView(R.id.editor_fragment_canvas)
    FrameLayout canvas;

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

        // get shape size in px
        int shapeSize = (int) getResources().getDimension(R.dimen.shape_size);

        // initialize the presenter
        presenter.init(canvas.getWidth(), canvas.getHeight(), shapeSize);
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
    public int drawShape(Shape shape) {
        switch (shape.getType()) {

            case SHAPE_TYPE_SQUARE:
                // add shape view to canvas and return its index
                return addViewToCanvas(new Square(getContext(), shape.getColor()), shape);

            case SHAPE_TYPE_CIRCLE:
                // add shape view to canvas and return its index
                return addViewToCanvas(new Circle(getContext(), shape.getColor()), shape);

            case SHAPE_TYPE_TRIANGLE:
                // add shape view to canvas and return its index
                return addViewToCanvas(new Triangle(getContext(), shape.getColor()), shape);

            default:
                return 0;
        }
    }


    @Override
    public void removeShape(int viewIndex) {
        // remove view with
        canvas.removeViewAt(viewIndex);
    }


    @Override
    public void setPresenter(EditorContract.Presenter presenter) {
        // used when manual injection of presenter
    }


    /**
     * Adds a shape on the canvas
     *
     * @param view
     * @param shape
     * @return
     */
    private int addViewToCanvas(View view, Shape shape) {

        // set shape size
        view.setLayoutParams(new ViewGroup.LayoutParams(shape.getWidth(), shape.getHeight()));

        // set shape position on canvas
        view.setX(shape.getPositionXAxis());
        view.setY(shape.getPositionYAxis());

        // add onClick listener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 28/10/2018 swap shape type
            }
        });

        // add view to canvas
        canvas.addView(view);

        // return index of added view on canvas
        return canvas.indexOfChild(view);
    }
}
