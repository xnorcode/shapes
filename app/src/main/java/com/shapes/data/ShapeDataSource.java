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
     * Get shape by its id
     *
     * @param id The shape's id
     * @return The shape
     */
    Single<Shape> find(int id);


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
     * Deletes all given shapes
     *
     * @param ids A list with all shape id's to be deleted
     * @return Observable of deletion status
     */
    Single<Boolean> delete(List<Integer> ids);


    /**
     * Deletes all shapes
     *
     * @return Observable of the deletion status
     */
    Single<Boolean> clear();
}
