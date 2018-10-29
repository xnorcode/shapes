package com.shapes.data.local;

import com.shapes.data.Shape;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by xnorcode on 29/10/2018.
 */
public class ShapeLocalDataSource implements DbHelper {

    private ShapeDao shapeDao;

    @Inject
    public ShapeLocalDataSource(ShapeDao shapeDao) {
        this.shapeDao = shapeDao;
    }

    @Override
    public List<Shape> getShapes() {
        return shapeDao.getShapes();
    }

    @Override
    public Shape getShape(int id) {
        return shapeDao.getShape(id);
    }

    @Override
    public void insertShape(Shape shape) {
        shapeDao.insertShape(shape);
    }

    @Override
    public void updateShape(Shape shape) {
        shapeDao.updateShape(shape);
    }

    @Override
    public void deleteShape(int id) {
        shapeDao.deleteShape(id);
    }

    @Override
    public void deleteShapes() {
        shapeDao.deleteShapes();
    }
}
