package com.shapes.data;

import com.shapes.data.local.ShapeLocalDataSource;
import com.shapes.utils.Constants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;
import static com.shapes.utils.Constants.SHAPE_TYPE_TRIANGLE;

/**
 * Created by xnorcode on 29/10/2018.
 */
public class ShapeRepositoryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Shape shape;

    private List<Shape> shapes;

    private EditorAction action;

    private List<EditorAction> actions;


    @Mock
    private ShapeLocalDataSource localDataSource;

    @InjectMocks
    private ShapeRepository shapeRepository;


    @Before
    public void setUp() {
        // create square shape
        shape = new Shape(2, SHAPE_TYPE_SQUARE);

        // add shape to list of shapes
        shapes = new ArrayList<>();
        shapes.add(shape);

        // create new action
        action = new EditorAction(2, Constants.EDITOR_ADD_SHAPE, 2);

        // add action to list
        actions = new ArrayList<>();
        actions.add(action);
    }

    @Test
    public void load() {
        // make mock data available
        Mockito.when(localDataSource.getShapes()).thenReturn(shapes);

        // call method and check data
        shapeRepository.load().test().assertValue(shapes);

        // verify local data source method called once
        Mockito.verify(localDataSource).getShapes();

        // check cache
        List<Shape> actual = new ArrayList<>(shapeRepository.shapesCache.values());
        Assert.assertEquals(shapes, actual);
    }

    @Test
    public void find() {
        // make mock data available
        Mockito.when(localDataSource.getShape(shape.getId())).thenReturn(shape);

        // call method and check data
        shapeRepository.find(shape.getId()).test().assertValue(shape);

        // verify local data source method called once
        Mockito.verify(localDataSource).getShape(shape.getId());
    }

    @Test
    public void save() {
        // call method and check status
        shapeRepository.save(shape).test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).insertShape(shape);
    }

    @Test
    public void update() {
        // change shape
        shape.setType(SHAPE_TYPE_TRIANGLE);

        //call method and check status
        shapeRepository.update(shape).test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).updateShape(shape);
    }

    @Test
    public void delete() {
        //call method and check status
        shapeRepository.delete(shape.getId()).test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).deleteShape(shape.getId());
    }

    @Test
    public void deleteAllShapes() {

        // create a list with all shape ids
        List<Integer> ids = new ArrayList<>();

        for (Shape shape : shapes) {
            ids.add(shape.getId());
        }

        //call method and check status
        shapeRepository.delete(ids).test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).deleteShape(shape.getId());
    }

    @Test
    public void clear() {
        //call method and check status
        shapeRepository.clear().test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).deleteShapes();
    }

    @Test
    public void loadActions() {
        // make mock data available
        Mockito.when(localDataSource.getActions()).thenReturn(actions);

        // call method and check data
        shapeRepository.loadActions().test().assertValue(actions);

        // verify local data source method called once
        Mockito.verify(localDataSource).getActions();

        // check cache
        List<EditorAction> actual = new ArrayList<>(shapeRepository.actionsCache.values());
        Assert.assertEquals(actions, actual);
    }

    @Test
    public void findAction() {
        // make mock data available
        Mockito.when(localDataSource.getAction(action.getId())).thenReturn(action);

        // call method and check data
        shapeRepository.findAction(action.getId()).test().assertValue(action);

        // verify local data source method called once
        Mockito.verify(localDataSource).getAction(action.getId());
    }

    @Test
    public void saveActions() {
        // call method and check status
        shapeRepository.saveActions(actions).test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).deleteActions();
        Mockito.verify(localDataSource).insertActions(actions);
    }

    @Test
    public void clearActions() {
        //call method and check status
        shapeRepository.clearActions().test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).deleteActions();
    }

    @Test
    public void deleteAction() {
        //call method and check status
        shapeRepository.deleteAction(action.getId()).test().assertValue(true);

        // verify local data source method called once
        Mockito.verify(localDataSource).deleteAction(action.getId());
    }
}