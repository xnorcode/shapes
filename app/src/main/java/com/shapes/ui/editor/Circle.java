package com.shapes.ui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class Circle extends View implements ShapeView {

    private Paint paint;

    private int radius = 0;

    private int cx = 0;

    private int cy = 0;


    public Circle(Context context) {
        super(context);
        // create paint
        paint = new Paint();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // calculate circle
        radius = Math.min(w, h) / 2;
        cx = w / 2;
        cy = h / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw on canvas
        canvas.drawCircle(cx, cy, radius, paint);
    }


    @Override
    public void tag(int id) {
        setTag(id);
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
        return getView();
    }
}
