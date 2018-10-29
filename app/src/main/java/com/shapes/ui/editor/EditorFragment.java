package com.shapes.ui.editor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

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
    EditorCanvas canvas;

    @Inject
    EditorContract.Presenter presenter;

    // TODO: 29/10/2018 Show 'Stats' button

    // TODO: 29/10/2018 Show Delete All button


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editor, container, false);

        ButterKnife.bind(this, rootView);

        canvas.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // remove listener
                canvas.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // get shape size in px
                int shapeSize = (int) getResources().getDimension(R.dimen.shape_size);

                // create canvas grid
                canvas.calculateGrids(shapeSize);

                // initialize the presenter
                presenter.init(canvas.getGridCapacity());
            }
        });

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        // bind this view to presenter
        presenter.setView(this);
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

    @OnClick(R.id.btn_undo)
    public void undo() {
        presenter.undoAction();
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
    public void removeShapeAt(int viewIndex) {
        // remove view with
        canvas.removeViewAt(viewIndex);
    }


    @Override
    public void showNotification(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
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
        view.setLayoutParams(new ViewGroup.LayoutParams(canvas.getGridSize(), canvas.getGridSize()));

        // set shape position on canvas
        view.setX(canvas.getPositionXForGrid(shape.getGridIndex()));
        view.setY(canvas.getPositionYForGrid(shape.getGridIndex()));

        // add onClick listener to swap shape type
        view.setOnClickListener(v -> {
            presenter.swapShape(shape.getId(), false);
        });

        // TODO: 29/10/2018 Remove shape on longClick()

        // TODO: 29/10/2018 undo deletion of the shape

        // add view to canvas
        canvas.addView(view, shape.getId() - 1);

        // return index of added view on canvas
        return canvas.indexOfChild(view);
    }
}
