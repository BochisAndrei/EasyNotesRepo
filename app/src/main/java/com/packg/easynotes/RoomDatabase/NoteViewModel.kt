package com.packg.easynotes.RoomDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.packg.easynotes.Elements.TextNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotes: LiveData<List<TextNote>>

    init {
        val notesDao = NotesRoomDatabase.getDatabase(application, viewModelScope).textNoteDao()
        repository = NoteRepository(notesDao)
        allNotes = repository.allNotes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: TextNote) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}