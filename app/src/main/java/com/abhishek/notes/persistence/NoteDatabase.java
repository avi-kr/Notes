package com.abhishek.notes.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.abhishek.notes.models.Note;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_db";

    public abstract NoteDao getNoteDao();
}