package com.shapes.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.shapes.data.local.DbHelper;
import com.shapes.data.local.ShapeDao;
import com.shapes.data.local.ShapeDatabase;
import com.shapes.data.local.ShapeLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xnorcode on 29/10/2018.
 */
@Module
public class ShapeRepositoryModule {

    @Singleton
    @Provides
    ShapeDataSource providesShapeRepository(DbHelper dbHelper) {
        return new ShapeRepository(dbHelper);
    }

    @Singleton
    @Provides
    DbHelper providesShapeLocalDataSource(ShapeDao dao) {
        return new ShapeLocalDataSource(dao);
    }

    @Singleton
    @Provides
    ShapeDao providesShapeDao(ShapeDatabase db) {
        return db.getShapesDao();
    }

    @Singleton
    @Provides
    ShapeDatabase providesDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), ShapeDatabase.class, "shapes.db")
                .build();
    }
}
