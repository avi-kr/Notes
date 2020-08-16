package com.abhishek.notes.ui.note;

import static com.abhishek.notes.repository.NoteRepository.NOTE_TITLE_NULL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.abhishek.notes.models.Note;
import com.abhishek.notes.repository.NoteRepository;
import com.abhishek.notes.ui.Resource;
import javax.inject.Inject;

/**
 * Created by Abhishek Kumar on 17/08/20.
 * (c)2020 VMock. All rights reserved.
 */
public class NoteViewModel extends ViewModel {

    private static final String TAG = "NoteViewModel";

    // inject
    private final NoteRepository noteRepository;

    // vars
    private MutableLiveData<Note> note = new MutableLiveData<>();

    @Inject
    public NoteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.insertNote(note.getValue())
        );
    }


    public LiveData<Note> observeNote() {
        return note;
    }

    public void setNote(Note note) throws Exception {
        if (note.getTitle() == null || note.getTitle().equals("")) {
            throw new Exception(NOTE_TITLE_NULL);
        }
        this.note.setValue(note);
    }

}
