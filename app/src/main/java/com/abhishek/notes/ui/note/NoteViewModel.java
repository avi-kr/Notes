package com.abhishek.notes.ui.note;

import static com.abhishek.notes.repository.NoteRepository.NOTE_TITLE_NULL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.abhishek.notes.models.Note;
import com.abhishek.notes.repository.NoteRepository;
import com.abhishek.notes.ui.Resource;
import com.abhishek.notes.util.DateUtil;
import javax.inject.Inject;

/**
 * Created by Abhishek Kumar on 17/08/20.
 * (c)2020 VMock. All rights reserved.
 */
public class NoteViewModel extends ViewModel {

    private static final String TAG = "NoteViewModel";

    public enum ViewState {VIEW, EDIT}

    // inject
    private final NoteRepository noteRepository;

    // vars
    private MutableLiveData<Note> note = new MutableLiveData<>();

    private MutableLiveData<ViewState> viewState = new MutableLiveData<>();

    private boolean isNewNote;


    @Inject
    public NoteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.insertNote(note.getValue())
        );
    }

    public LiveData<Resource<Integer>> updateNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.updateNote(note.getValue())
        );
    }

    public LiveData<Note> observeNote() {
        return note;
    }

    public LiveData<ViewState> observeViewState() {
        return viewState;
    }

    public void setViewState(ViewState viewState) {
        this.viewState.setValue(viewState);
    }

    public void setIsNewNote(boolean isNewNote) {
        this.isNewNote = isNewNote;
    }

    public LiveData<Resource<Integer>> saveNote() {
        return null;
    }

    public void updateNote(String title, String content) throws Exception {
        if (title == null || title.equals("")) {
            throw new NullPointerException("Title can't be null");
        }
        String temp = removeWhiteSpace(content);
        if (temp.length() > 0) {
            Note updatedNote = new Note(note.getValue());
            updatedNote.setTitle(title);
            updatedNote.setContent(content);
            updatedNote.setTimestamp(DateUtil.getCurrentTimeStamp());

            note.setValue(updatedNote);
        }
    }

    private String removeWhiteSpace(String string) {
        string = string.replace("\n", "");
        string = string.replace(" ", "");
        return string;
    }

    public void setNote(Note note) throws Exception {
        if (note.getTitle() == null || note.getTitle().equals("")) {
            throw new Exception(NOTE_TITLE_NULL);
        }
        this.note.setValue(note);
    }

    public boolean shouldNavigateBack() {
        if (viewState.getValue() == ViewState.VIEW) {
            return true;
        } else {
            return false;
        }
    }

}
