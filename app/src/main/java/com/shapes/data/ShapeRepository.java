package com.shapes.data;

import com.shapes.data.local.DbHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by xnorcode on 29/10/2018.
 */
@Singleton
public class ShapeRepository implements ShapeDataSource {

    private DbHelper shapeLocalDataSource;


    @Inject
    public ShapeRepository(DbHelper shapeLocalDataSource) {
        this.shapeLocalDataSource = shapeLocalDataSource;
    }


    @Override
    public Single<List<Shape>> load() {
        return Single.<List<Shape>>create(emitter -> {
            List<Shape> shapes = shapeLocalDataSource.getShapes();
            if (shapes == null) emitter.onError(new Exception("Error loading shapes..."));
            emitter.onSuccess(shapes);
        });
    }

    @Override
    public Single<Boolean> save(Shape shape) {
        return Single.<Boolean>create(emitter -> {
            shapeLocalDataSource.insertShape(shape);
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> update(Shape shape) {
        return Single.<Boolean>create(emitter -> {
            shapeLocalDataSource.updateShape(shape);
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> delete(int id) {
        return Single.<Boolean>create(emitter -> {
            shapeLocalDataSource.deleteShape(id);
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> clear() {
        return Single.<Boolean>create(emitter -> {
            shapeLocalDataSource.deleteShapes();
            emitter.onSuccess(true);
        });
    }
}
