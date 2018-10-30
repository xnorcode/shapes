package com.shapes.data.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.shapes.data.EditorAction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.shapes.utils.Constants.SHAPE_TYPE_SQUARE;

/**
 * Created by xnorcode on 30/10/2018.
 */
public class EditorActionDaoTest {

    private ShapeDatabase database;

    private EditorAction action;

    private List<EditorAction> actions;


    @Before
    public void setUp() {
        // init db
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ShapeDatabase.class).build();

        // init action
        action = new EditorAction(2, SHAPE_TYPE_SQUARE, 3);

        // add to list
        actions = new ArrayList<>();
        actions.add(action);
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void getActions() {
        // insert actions in db
        database.getEditorActionDao().insertActions(actions);

        // get all actions
        List<EditorAction> actual = database.getEditorActionDao().getActions();

        Assert.assertEquals(actions, actual);
    }

    @Test
    public void getAction() {
        // insert actions in db
        database.getEditorActionDao().insertActions(actions);

        // get actions by id
        EditorAction actual = database.getEditorActionDao().getAction(action.getId());

        Assert.assertEquals(action, actual);
    }

    @Test
    public void deleteActions() {
        // insert actions in db
        database.getEditorActionDao().insertActions(actions);

        // delete actions in db
        database.getEditorActionDao().deleteActions();

        // get all shapes
        List<EditorAction> actual = database.getEditorActionDao().getActions();

        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void deleteAction() {
        // insert actions in db
        database.getEditorActionDao().insertActions(actions);

        // delete action in db
        database.getEditorActionDao().deleteAction(action.getId());

        // get all actions
        List<EditorAction> actual = database.getEditorActionDao().getActions();

        Assert.assertEquals(0, actual.size());
    }
}