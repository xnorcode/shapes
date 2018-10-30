package com.shapes.data;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by xnorcode on 30/10/2018.
 */
public interface EditorActionDataSource {


    /**
     * Gets all actions
     *
     * @return list of actions in single
     */
    Single<List<EditorAction>> loadActions();


    /**
     * Get action by id
     *
     * @param id action id
     * @return action in single
     */
    Single<EditorAction> findAction(int id);


    /**
     * save all actions
     *
     * @param actions list of actions
     * @return status
     */
    Single<Boolean> saveActions(List<EditorAction> actions);


    /**
     * Deletes all actions
     *
     * @return status
     */
    Single<Boolean> clearActions();


    /**
     * Deletes action by id
     *
     * @param id the action id
     * @return status
     */
    Single<Boolean> deleteAction(int id);
}
