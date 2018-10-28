package com.shapes.ui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class Circle extends View {

    private Paint paint;

    private int radius = 0;

    private int cx = 0;

    private int cy = 0;


    public Circle(Context context, int color) {
        super(context);

        // create paint
        paint = new Paint();

        // set color
        paint.setColor(color);
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
}
