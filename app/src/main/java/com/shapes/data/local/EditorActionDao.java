package com.shapes.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.shapes.data.EditorAction;

import java.util.List;

/**
 * Created by xnorcode on 30/10/2018.
 */
@Dao
public interface EditorActionDao {

    @Query(" SELECT * FROM actions")
    List<EditorAction> getActions();

    @Query(" SELECT * FROM actions WHERE id = :id")
    EditorAction getAction(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertActions(List<EditorAction> actions);

    @Query(" DELETE FROM actions")
    void deleteActions();

    @Query(" DELETE FROM actions WHERE id = :id")
    void deleteAction(int id);
}
