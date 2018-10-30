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


    @Inject
    public ShapeRepository(DbHelper shapeLocalDataSource) {
        this.shapeLocalDataSource = shapeLocalDataSource;
        this.shapesCache = new HashMap<>();
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
    public Single<Boolean> delete(List<Shape> shapes) {
        return Single.<Boolean>create(emitter -> {

            for (Shape shape : shapes) {

                // delete from db
                shapeLocalDataSource.deleteShape(shape.getId());

                // remove from cache
                shapesCache.remove(shape.getId());
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
}
