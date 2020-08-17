package com.abhishek.notes.di;

import com.abhishek.notes.ui.note.NoteActivity;
import com.abhishek.notes.ui.noteslist.NotesListActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();

    @ContributesAndroidInjector
    abstract NoteActivity contributeNotesActivity();
}
