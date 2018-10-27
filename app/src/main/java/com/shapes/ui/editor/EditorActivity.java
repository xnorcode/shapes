package com.shapes.ui.editor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shapes.R;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // remove activity's background theme
        getWindow().setBackgroundDrawable(null);

        // Add fragment to activity
        EditorFragment fragment = (EditorFragment) getSupportFragmentManager().findFragmentById(R.id.editor_fragment_container);
        if (fragment == null) {
            fragment = new EditorFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.editor_fragment_container, fragment)
                    .commit();
        }
    }
}
