package com.abhishek.notes.respository;

import static com.abhishek.notes.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.abhishek.notes.repository.NoteRepository.UPDATE_FAILURE;
import static com.abhishek.notes.repository.NoteRepository.UPDATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.abhishek.notes.models.Note;
import com.abhishek.notes.persistence.NoteDao;
import com.abhishek.notes.repository.NoteRepository;
import com.abhishek.notes.ui.Resource;
import com.abhishek.notes.util.TestUtil;
import io.reactivex.Single;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.*;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class NoteRepositoryTest {

    private static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);

    // system under test
    private NoteRepository noteRepository;

    private NoteDao noteDao;

    @BeforeEach
    public void initEach() {
        noteDao = mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }


    /**
     * insert note
     * verify the correct method is called
     * confirm observer is triggered
     * confirm new rows inserted
     */
    @Test
    void insertNote_returnRow() throws Exception {
        // Arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingSingle();

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: " + returnedValue.data);
        assertEquals(Resource.success(1, NoteRepository.INSERT_SUCCESS), returnedValue);

//        // Or test using RxJava
//        noteRepository.insertNote(NOTE1)
//                .test()
//                .await()
//                .assertValue(Resource.success(1, INSERT_SUCCESS));
    }

    /**
     * Insert note
     * Failure (return -1)
     */
    @Test
    void insertNote_returnFailure() throws Exception {
        // Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, NoteRepository.INSERT_FAILURE), returnedValue);
    }


    /**
     * insert note
     * null title
     * confirm throw exception
     */
    @Test
    void insertNote_nullTitle_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

    /**
     * update note
     * verify correct method is called
     * confirm observer is trigger
     * confirm number of rows updated
     */
    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {
        // Arrange
        final int updatedRow = 1;
        when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(updatedRow));

        // Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);
    }


    /**
     * update note
     * Failure (-1)
     */
    @Test
    void updateNote_returnFailure() throws Exception {
        // Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        when(noteDao.updateNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, UPDATE_FAILURE), returnedValue);
    }
    /**
     * update note
     * null title
     * throw exception
     */
    @Test
    void updateNote_nullTitle_throwException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note  = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

}
