package com.shapes.ui.editor;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xnorcode on 27/10/2018.
 */
@Module
public abstract class EditorModule {

    @ContributesAndroidInjector
    abstract EditorFragment providesEditorFragment();

    @Binds
    abstract EditorContract.Presenter providesPresenter(EditorPresenter presenter);
}
