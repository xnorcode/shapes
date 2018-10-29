package com.shapes.di;

import android.app.Application;

import com.shapes.ShapesApp;
import com.shapes.data.ShapeRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by xnorcode on 27/10/2018.
 */
@Singleton
@Component(modules = {ApplicationModule.class,
        ShapeRepositoryModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<ShapesApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}