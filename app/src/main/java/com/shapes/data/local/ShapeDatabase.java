package com.shapes.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.shapes.data.EditorAction;
import com.shapes.data.Shape;

/**
 * Created by xnorcode on 29/10/2018.
 * <p>
 * The room database that contains our shapes
 */
@Database(entities = {Shape.class, EditorAction.class}, version = 1)
public abstract class ShapeDatabase extends RoomDatabase {

    public abstract ShapeDao getShapesDao();

    public abstract EditorActionDao getEditorActionDao();
}
