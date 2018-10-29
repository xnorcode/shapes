package com.shapes.data.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.shapes.data.Shape;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;
import static com.shapes.utils.Constants.SHAPE_TYPE_TRIANGLE;

/**
 * Created by xnorcode on 29/10/2018.
 */
public class ShapeDaoTest {

    private ShapeDatabase database;

    private Shape shape;

    private List<Shape> shapes;


    @Before
    public void setUp() {
        // init db
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ShapeDatabase.class).build();

        // init shape
        shape = new Shape(2, SHAPE_TYPE_SQUARE);

        // add to list
        shapes = new ArrayList<>();
        shapes.add(shape);
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getShapes() {
        // insert shape in db
        database.getShapesDao().insertShape(shape);

        // get all shapes
        List<Shape> actual = database.getShapesDao().getShapes();

        Assert.assertEquals(shapes, actual);
    }

    @Test
    public void updateShape() {
        // insert shape in db
        database.getShapesDao().insertShape(shape);

        // update shape
        shape.setType(SHAPE_TYPE_TRIANGLE);

        // update shape in db
        database.getShapesDao().updateShape(shape);

        // get all shapes
        List<Shape> actual = database.getShapesDao().getShapes();

        Assert.assertEquals(SHAPE_TYPE_TRIANGLE, actual.get(0).getType());
    }

    @Test
    public void deleteShape() {
        // insert shape in db
        database.getShapesDao().insertShape(shape);

        // delete shape in db
        database.getShapesDao().deleteShape(shape.getId());

        // get all shapes
        List<Shape> actual = database.getShapesDao().getShapes();

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void deleteShapes() {
        // insert shape in db
        database.getShapesDao().insertShape(shape);

        // delete shape in db
        database.getShapesDao().deleteShapes();

        // get all shapes
        List<Shape> actual = database.getShapesDao().getShapes();

        Assert.assertEquals(0, actual.size());
    }
}