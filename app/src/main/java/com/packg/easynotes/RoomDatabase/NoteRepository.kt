package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.Element
import com.packg.easynotes.Elements.TextNote

class NoteRepository(private val textNoteDao: TextNoteDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<TextNote>> = textNoteDao.getOrderedNotes()
    val allFavoriteNotes: LiveData<List<TextNote>> = textNoteDao.getOrderedFavoriteNotes()
    val allTrashedNotes: LiveData<List<TextNote>> = textNoteDao.getOrderedTrashedNotes()


    suspend fun insert(note: TextNote) {
        textNoteDao.insert(note)
    }
    suspend fun update(note: TextNote) {
        textNoteDao.update(note)
    }
    suspend fun updateFavorite(id: Long, favorite: Boolean){
        textNoteDao.updateFavorite(id, favorite)
    }
    suspend fun updateTrash(id: Long, trash: Boolean){
        textNoteDao.updateTrash(id, trash)
    }
    suspend fun delete(note: TextNote){
        textNoteDao.delete(note)
    }

}