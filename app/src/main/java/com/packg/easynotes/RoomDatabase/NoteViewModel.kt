package com.packg.easynotes.RoomDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.Folder
import com.packg.easynotes.Elements.TextNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryNotes: NoteRepository
    private val repositoryCrossNotes: CrossNoteRepository
    private val repositoryFolders: FolderRepository
    private val repositoryCheckBoxNote: CheckBoxRepository



    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotes : LiveData<List<TextNote>>
    val allCrossNotes : LiveData<List<CrossNote>>
    val allFolderNotes : LiveData<List<Folder>>
    val allCheckBoxes : LiveData<List<CheckBoxNote>>

    init {
        val notesDao = NotesRoomDatabase.getDatabase(application, viewModelScope).textNoteDao()
        repositoryNotes = NoteRepository(notesDao)
        allNotes = repositoryNotes.allNotes
        val crossNotesDao = NotesRoomDatabase.getDatabase(application, viewModelScope).crossNoteDao()
        repositoryCrossNotes = CrossNoteRepository(crossNotesDao)
        allCrossNotes = repositoryCrossNotes.allNotes
        //allCheckBoxes = repositoryCrossNotes.crossNoteAndCheckBoxNote
        val folderDao = NotesRoomDatabase.getDatabase(application, viewModelScope).folderDao()
        repositoryFolders = FolderRepository(folderDao)
        allFolderNotes = repositoryFolders.allNotes
        val checkBoxDao = NotesRoomDatabase.getDatabase(application, viewModelScope).checkBoxDao()
        repositoryCheckBoxNote = CheckBoxRepository(checkBoxDao)
        allCheckBoxes = repositoryCheckBoxNote.allCheckBoxNotes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: TextNote) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNotes.insert(note)
    }
    fun update(note: TextNote) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNotes.update(note)
    }
    fun insert(note: CrossNote) = viewModelScope.launch(Dispatchers.IO) {
        repositoryCrossNotes.insert(note)
    }
    fun update(note: CrossNote) = viewModelScope.launch(Dispatchers.IO) {
        repositoryCrossNotes.update(note)
    }
    fun insert(note: Folder) = viewModelScope.launch(Dispatchers.IO) {
        repositoryFolders.insert(note)
    }
    fun insert(note: CheckBoxNote) = viewModelScope.launch(Dispatchers.IO) {
        repositoryCheckBoxNote.insert(note)
    }

    fun insert(note: CrossNote, checkBoxes: List<CheckBoxNote>)  = viewModelScope.launch(Dispatchers.IO) {
        val id = repositoryCrossNotes.insert(note)
        for(checkBox in checkBoxes){
            checkBox.parentId = id
        }
        repositoryCheckBoxNote.insertAll(checkBoxes)
    }
}