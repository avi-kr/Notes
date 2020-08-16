package com.abhishek.notes;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import com.abhishek.notes.persistence.NoteDao;
import com.abhishek.notes.persistence.NoteDatabase;
import org.junit.*;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */
public abstract class NoteDatabaseTest {

    // system under test
    private NoteDatabase noteDatabase;

    public NoteDao getNoteDao() {
        return noteDatabase.getNoteDao();
    }

    @Before
    public void init() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish() {
        noteDatabase.close();
    }
}

