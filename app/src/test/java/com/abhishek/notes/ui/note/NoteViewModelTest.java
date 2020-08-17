package com.abhishek.notes.ui.note;

import static com.abhishek.notes.repository.NoteRepository.INSERT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.abhishek.notes.models.Note;
import com.abhishek.notes.repository.NoteRepository;
import com.abhishek.notes.ui.Resource;
import com.abhishek.notes.util.InstantExecutorExtension;
import com.abhishek.notes.util.LiveDataTestUtil;
import com.abhishek.notes.util.TestUtil;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.single.SingleToFlowable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.function.*;
import org.mockito.*;

/**
 * Created by Abhishek Kumar on 17/08/20.
 * (c)2020 VMock. All rights reserved.
 */
@ExtendWith(InstantExecutorExtension.class)
class NoteViewModelTest {

    // system under test
    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }

    /**
     * can't observe a note that hasn't been set
     */
    @Test
    void observeEmptyNoteWhenNoteSet() throws Exception {
        // Arrange
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        // Act
        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert
        assertNull(note);
    }

    /**
     * Observe a note has been set and onChanged will trigger in activity
     */
    @Test
    void observeNote_whenSet() throws Exception {
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        // Act
        noteViewModel.setNote(note);
        Note observedNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert
        assertEquals(note, observedNote);
    }

    /**
     * Insert a new note and observe row returned
     */
    @Test
    void insertNote_returnRow() throws Exception {
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow = 1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow, INSERT_SUCCESS));
        when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.insertNote());

        // Assert
        assertEquals(Resource.success(insertedRow, INSERT_SUCCESS), returnedValue);
    }

    /**
     * insert: dont return a new row without observer
     */
    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception {
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Act
        noteViewModel.setNote(note);

        // Assert
        verify(noteRepository, never()).insertNote(any(Note.class));
    }

    /**
     * set note, null title, throw exception
     */
    @Test
    void setNote_nullTitle_throwException() throws Exception {
        // Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        // Assert
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {

                // Act
                noteViewModel.setNote(note);
            }
        });
    }

}
