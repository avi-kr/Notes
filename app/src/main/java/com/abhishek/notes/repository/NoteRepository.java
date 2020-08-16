package com.abhishek.notes.repository;

import androidx.annotation.NonNull;
import com.abhishek.notes.persistence.NoteDao;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */
@Singleton
public class NoteRepository {

    // inject
    @NonNull
    private final NoteDao noteDao;

    @Inject
    public NoteRepository(@NonNull NoteDao noteDao) {
        this.noteDao = noteDao;
    }

}
