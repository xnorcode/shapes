package com.shapes.ui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class Square extends View {

    private Paint paint;

    private Rect rect;


    public Square(Context context, int color) {
        super(context);

        // create paint
        paint = new Paint();

        // set color
        paint.setColor(color);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // create rectangle
        rect = new Rect(0, 0, w, h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw square on canvas
        canvas.drawRect(rect, paint);
    }
}
