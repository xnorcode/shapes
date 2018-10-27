package com.shapes.di;

import com.shapes.ui.editor.EditorActivity;
import com.shapes.ui.editor.EditorModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xnorcode on 27/10/2018.
 */
@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = EditorModule.class)
    abstract EditorActivity providesEditorActivity();
}
