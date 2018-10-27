package com.shapes.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.shapes.utils.Constants.SHAPE_TYPE_CIRCLE;
import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;
import static com.shapes.utils.Constants.SHAPE_TYPE_TRIANGLE;

/**
 * Created by xnorcode on 27/10/2018.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({SHAPE_TYPE_SQUARE, SHAPE_TYPE_CIRCLE, SHAPE_TYPE_TRIANGLE})
public @interface SHAPE_TYPE {
}
