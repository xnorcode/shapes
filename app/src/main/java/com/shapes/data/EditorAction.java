package com.shapes.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by xnorcode on 30/10/2018.
 */
@Entity(tableName = "actions")
public class EditorAction {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "type")
    @SHAPE_TYPE
    private int type;

    @ColumnInfo(name = "shapeId")
    private int shapeId;

    public EditorAction(int id, @SHAPE_TYPE int type, int shapeId) {
        this.id = id;
        this.type = type;
        this.shapeId = shapeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShapeId() {
        return shapeId;
    }

    public void setShapeId(int shapeId) {
        this.shapeId = shapeId;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + type;
        result = 31 * result + shapeId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        EditorAction action = (EditorAction) obj;

        return action.getId() == this.id
                && action.getType() == this.type
                && action.getShapeId() == this.shapeId;
    }
}
