package com.shapes.ui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class Triangle extends View {

    private Paint paint;

    private Path path;


    public Triangle(Context context, int color) {
        super(context);

        // create paint
        paint = new Paint();

        // set color
        paint.setColor(color);
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
