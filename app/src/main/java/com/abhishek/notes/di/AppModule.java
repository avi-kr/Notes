package com.abhishek.notes.di;

import static com.abhishek.notes.persistence.NoteDatabase.DATABASE_NAME;

import android.app.Application;
import androidx.room.Room;
import com.abhishek.notes.persistence.NoteDao;
import com.abhishek.notes.persistence.NoteDatabase;
import com.abhishek.notes.repository.NoteRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase) {
        return noteDatabase.getNoteDao();
    }

    @Singleton
    @Provides
    static NoteRepository provideNoteRepository(NoteDao noteDao) {
        return new NoteRepository(noteDao);
    }

}