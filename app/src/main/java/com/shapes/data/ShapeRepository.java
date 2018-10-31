package com.shapes.data;

import com.shapes.data.local.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by xnorcode on 29/10/2018.
 */
@Singleton
public class ShapeRepository implements ShapeDataSource {

    private DbHelper shapeLocalDataSource;

    Map<Integer, Shape> shapesCache;

    Map<Integer, EditorAction> actionsCache;


    @Inject
    public ShapeRepository(DbHelper shapeLocalDataSource) {
        this.shapeLocalDataSource = shapeLocalDataSource;
        this.shapesCache = new HashMap<>();
        this.actionsCache = new HashMap<>();
    }


    @Override
    public Single<List<Shape>> load() {

        // check cache first
        if (!shapesCache.isEmpty()) {
            return Single.just(new ArrayList<Shape>(shapesCache.values()));
        }

        return Single.<List<Shape>>create(emitter -> {
            // get from db
            List<Shape> shapes = shapeLocalDataSource.getShapes();

            // error check
            if (shapes == null) emitter.onError(new Exception("Error loading shapes..."));

            // cache all shapes
            for (Shape shape : shapes) {
                if (!shapesCache.containsKey(shape.getId())) {
                    shapesCache.put(shape.getId(), shape);
                }
            }

            // proceed
            emitter.onSuccess(shapes);
        });
    }

    @Override
    public Single<Shape> find(int id) {

        // check cache first
        if (!shapesCache.isEmpty() && shapesCache.containsKey(id)) {
            return Single.just(shapesCache.get(id));
        }

        return Single.<Shape>create(emitter -> {
            // get from db
            Shape shape = shapeLocalDataSource.getShape(id);

            // error check
            if (shape == null) emitter.onError(new Exception("Could not find shape..."));

            // cache shape
            shapesCache.put(shape.getId(), shape);

            // proceed
            emitter.onSuccess(shape);
        });
    }

    @Override
    public Single<Boolean> save(Shape shape) {
        return Single.<Boolean>create(emitter -> {
            // add shape
            shapeLocalDataSource.insertShape(shape);

            // cache shape
            if (!shapesCache.containsKey(shape.getId())) {
                shapesCache.put(shape.getId(), shape);
            }

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> update(Shape shape) {
        return Single.<Boolean>create(emitter -> {
            // update shape
            shapeLocalDataSource.updateShape(shape);

            // cache shape
            shapesCache.put(shape.getId(), shape);

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> delete(int id) {
        return Single.<Boolean>create(emitter -> {
            // delete shape
            shapeLocalDataSource.deleteShape(id);

            // remove from cache
            shapesCache.remove(id);

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> delete(List<Integer> ids) {
        return Single.<Boolean>create(emitter -> {

            for (int id : ids) {

                // delete from db
                shapeLocalDataSource.deleteShape(id);

                // remove from cache
                shapesCache.remove(id);
            }

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> clear() {
        return Single.<Boolean>create(emitter -> {
            // clear db
            shapeLocalDataSource.deleteShapes();

            // clear cache
            shapesCache.clear();

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<List<EditorAction>> loadActions() {
        // check cache first
        if (!actionsCache.isEmpty()) {
            return Single.just(new ArrayList<EditorAction>(actionsCache.values()));
        }

        return Single.<List<EditorAction>>create(emitter -> {
            // get from db
            List<EditorAction> actions = shapeLocalDataSource.getActions();

            // error check
            if (actions == null)
                emitter.onError(new Exception("Error loading latest editor actions..."));

            // cache all actions
            for (EditorAction action : actions) {
                if (!actionsCache.containsKey(action.getId())) {
                    actionsCache.put(action.getId(), action);
                }
            }

            // proceed
            emitter.onSuccess(actions);
        });
    }

    @Override
    public Single<EditorAction> findAction(int id) {
        // check cache first
        if (!actionsCache.isEmpty() && actionsCache.containsKey(id)) {
            return Single.just(actionsCache.get(id));
        }

        return Single.<EditorAction>create(emitter -> {
            // get from db
            EditorAction action = shapeLocalDataSource.getAction(id);

            // error check
            if (action == null) emitter.onError(new Exception("Could not find action..."));

            // cache action
            actionsCache.put(action.getId(), action);

            // proceed
            emitter.onSuccess(action);
        });
    }

    @Override
    public Single<Boolean> saveActions(List<EditorAction> actions) {
        return Single.<Boolean>create(emitter -> {

            // delete all from db
            shapeLocalDataSource.deleteActions();

            // add all action
            shapeLocalDataSource.insertActions(actions);

            // cache actions
            actionsCache.clear();
            for (EditorAction action : actions) {
                actionsCache.put(action.getId(), action);
            }

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> clearActions() {
        return Single.<Boolean>create(emitter -> {
            // clear actions db
            shapeLocalDataSource.deleteActions();

            // clear cache
            actionsCache.clear();

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> deleteAction(int id) {
        return Single.<Boolean>create(emitter -> {
            // delete action
            shapeLocalDataSource.deleteAction(id);

            // remove from cache
            actionsCache.remove(id);

            // proceed
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> deleteActions(List<Integer> ids) {
        return Single.<Boolean>create(emitter -> {

            for (int id : ids) {

                // delete from db
                shapeLocalDataSource.deleteAction(id);

                // remove from cache
                actionsCache.remove(id);
            }

            // proceed
            emitter.onSuccess(true);
        });
    }
}
