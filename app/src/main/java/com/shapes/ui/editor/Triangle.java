package com.shapes.ui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class Triangle extends View implements ShapeView {

    private Paint paint;

    private Path path;


    public Triangle(Context context) {
        super(context);
        // create paint
        paint = new Paint();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // create path
        createPath(w, h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw triangle on canvas
        canvas.drawPath(path, paint);
    }


    @Override
    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void setSize(int size) {
        setLayoutParams(new ViewGroup.LayoutParams(size, size));
    }

    @Override
    public void positionOnCanvasAt(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public void onClick(OnClickListener listener) {
        setOnClickListener(listener);
    }

    @Override
    public void onLongClick(OnLongClickListener listener) {
        setOnLongClickListener(listener);
    }

    @Override
    public View getView() {
        return this;
    }

    /**
     * Create path
     *
     * @param w The width of the view
     * @param h The height of the view
     */
    private void createPath(int w, int h) {

        // set draw points
        Point p1 = new Point(w / 2, 0);
        Point p2 = new Point(0, h);
        Point p3 = new Point(w, h);

        // create draw paths
        path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.lineTo(p1.x, p1.y);
        path.close();
    }
}
