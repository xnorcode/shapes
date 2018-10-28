package com.shapes.ui.editor;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.shapes.utils.Constants.EDITOR_ADD_SHAPE;
import static com.shapes.utils.Constants.EDITOR_SWAP_SHAPE;

/**
 * Created by xnorcode on 28/10/2018.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({EDITOR_ADD_SHAPE, EDITOR_SWAP_SHAPE})
public @interface EDITOR_ACTION {
}
