package com.shapes.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shapes.data.Shape;

import java.util.List;

/**
 * Created by xnorcode on 29/10/2018.
 * <p>
 * Data Access Object for shapes table
 */
@Dao
public interface ShapeDao {

    @Query(" SELECT * FROM shapes")
    List<Shape> getShapes();

    @Query(" SELECT * FROM shapes WHERE id = :id")
    Shape getShape(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShape(Shape shape);

    @Update
    void updateShape(Shape shape);

    @Query(" DELETE FROM shapes WHERE id = :id")
    void deleteShape(int id);

    @Query(" DELETE FROM shapes")
    void deleteShapes();

}
