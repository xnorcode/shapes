package com.shapes.ui.editor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.shapes.R;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class Square extends View implements ShapeView {

    private Paint paint;

    private Rect rect;


    public Square(Context context) {
        super(context);
        // create paint
        paint = new Paint();
    }

    public Square(Context context, AttributeSet attrs) {
        super(context, attrs);

        // create paint
        paint = new Paint();

        // set color assigned on xml attributes
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.shapeViewAttr);
        try {
            setColor(types.getInteger(R.styleable.shapeViewAttr_shapeColor, 0));
        } finally {
            types.recycle();
        }
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
}
