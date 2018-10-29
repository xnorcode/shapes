package com.shapes.data;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by xnorcode on 29/10/2018.
 */
public interface ShapeDataSource {


    /**
     * Gets all shapes
     *
     * @return Observable of list with all shapes
     */
    Single<List<Shape>> load();


    /**
     * Saves a shape
     *
     * @param shape The shape to be saved
     * @return Observable of save status
     */
    Single<Boolean> save(Shape shape);


    /**
     * Updates a shape
     *
     * @param shape The shape to be updated
     * @return Observable of update status
     */
    Single<Boolean> update(Shape shape);


    /**
     * Deletes a shape
     *
     * @param id The id of the shape to be deleted
     * @return Observable of deletion status
     */
    Single<Boolean> delete(int id);


    /**
     * Deletes all shapes
     *
     * @return Observable of the deletion status
     */
    Single<Boolean> clear();
}
