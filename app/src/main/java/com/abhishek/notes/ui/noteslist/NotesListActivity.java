package com.abhishek.notes.ui.noteslist;

import android.os.Bundle;
import android.util.Log;
import com.abhishek.notes.R;
import com.abhishek.notes.repository.NoteRepository;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */

public class NotesListActivity extends DaggerAppCompatActivity {

    private static final String TAG = "NotesListActivity";

    @Inject
    NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        Log.d(TAG, "onCreate: " + noteRepository);
    }
}
