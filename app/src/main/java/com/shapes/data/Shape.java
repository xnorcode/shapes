package com.shapes.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static com.shapes.utils.Constants.SHAPE_TYPE_CIRCLE;
import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;
import static com.shapes.utils.Constants.SHAPE_TYPE_TRIANGLE;

/**
 * Created by xnorcode on 27/10/2018.
 */
@Entity(tableName = "shapes")
public class Shape {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "type")
    @SHAPE_TYPE
    private int type;

    @ColumnInfo(name = "color")
    private int color;

    @ColumnInfo(name = "gridIndex")
    private int gridIndex;

    public Shape(int id) {
        this.id = id;
    }

    @Ignore
    public Shape(int id, @SHAPE_TYPE int type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    @SHAPE_TYPE
    public int getType() {
        return type;
    }

    public void setType(@SHAPE_TYPE int type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getGridIndex() {
        return gridIndex;
    }

    public void setGridIndex(int gridIndex) {
        this.gridIndex = gridIndex;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + type;
        result = 31 * result + color;
        result = 31 * result + gridIndex;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Shape shape = (Shape) obj;

        return shape.getId() == this.id
                && shape.getType() == this.type
                && shape.getColor() == this.color
                && shape.getGridIndex() == this.gridIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SHAPE with id: ");
        sb.append(id);
        sb.append(", type: ");
        switch (type) {
            case SHAPE_TYPE_SQUARE:
                sb.append("square");
                break;
            case SHAPE_TYPE_CIRCLE:
                sb.append("circle");
                break;
            case SHAPE_TYPE_TRIANGLE:
                sb.append("triangle");
                break;
        }
        sb.append(", color: ");
        sb.append(color);
        sb.append(", at grid: #");
        sb.append(gridIndex);
        return sb.toString();
    }
}
