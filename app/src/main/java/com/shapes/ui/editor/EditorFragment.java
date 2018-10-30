package com.shapes.ui.editor;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.shapes.R;
import com.shapes.data.Shape;
import com.shapes.ui.stats.StatsActivity;
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_editor_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: 29/10/2018 Show Delete All button
        if (item.getItemId() == R.id.editor_settings_action) {
            startActivity(new Intent(getContext(), StatsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


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
                return addViewToCanvas(new Square(getContext()), shape);

            case SHAPE_TYPE_CIRCLE:
                // add shape view to canvas and return its index
                return addViewToCanvas(new Circle(getContext()), shape);

            case SHAPE_TYPE_TRIANGLE:
                // add shape view to canvas and return its index
                return addViewToCanvas(new Triangle(getContext()), shape);

            default:
                return 0;
        }
    }


    @Override
    public void removeShape(int id) {
        // remove view with
        canvas.removeView(canvas.findViewWithTag(id));

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
     * Add shape on canvas
     *
     * @param shapeView The shapes view interface
     * @param shape     The shape data model
     * @return The canvas view index of added shape view
     */
    private int addViewToCanvas(ShapeView shapeView, Shape shape) {

        // set color
        shapeView.setColor(shape.getColor());

        // set shape size
        shapeView.setSize(canvas.getGridSize());

        // set shape position on canvas
        int x = canvas.getPositionXForGrid(shape.getGridIndex());
        int y = canvas.getPositionYForGrid(shape.getGridIndex());
        shapeView.positionOnCanvasAt(x, y);

        // add onClick listener to swap shape type
        shapeView.onClick(v -> {
            presenter.swapShape(shape.getId(), false);
        });

        shapeView.onLongClick(v -> false);


        // TODO: 29/10/2018 Remove shape on longClick() and include action to undo stack

        canvas.addView(shapeView.getView());

        // return index of added view on canvas
        return canvas.indexOfChild(shapeView.getView());
    }
}
