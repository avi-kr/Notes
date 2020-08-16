package com.abhishek.notes.respository;

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
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });
    }

}
