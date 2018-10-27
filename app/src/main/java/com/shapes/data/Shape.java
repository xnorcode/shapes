package com.shapes.data;

/**
 * Created by xnorcode on 27/10/2018.
 */
public class Shape {

    private int id;

    @SHAPE_TYPE
    private int type;

    private int height, width;

    private int color;

    private int positionXAxis, positionYAxis;

    public Shape(int id) {
        this.id = id;
    }

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPositionXAxis() {
        return positionXAxis;
    }

    public void setPositionXAxis(int positionXAxis) {
        this.positionXAxis = positionXAxis;
    }

    public int getPositionYAxis() {
        return positionYAxis;
    }

    public void setPositionYAxis(int positionYAxis) {
        this.positionYAxis = positionYAxis;
    }
}
