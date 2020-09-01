package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.CrossNoteWithCheckBoxes
import com.packg.easynotes.Elements.TextNote

class CrossNoteRepository(private val crossNoteDao: CrossNoteDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<CrossNote>> = crossNoteDao.getOrderedCrossNotes()
    val allFavoriteNotes: LiveData<List<CrossNote>> = crossNoteDao.getOrderedFavoriteCrossNotes()
    val allTrashedNotes: LiveData<List<CrossNote>> = crossNoteDao.getOrderedTrashedCrossNotes()

    suspend fun insert(note: CrossNote) : Long{
        return crossNoteDao.insert(note)
    }
    suspend fun update(note: CrossNote){
        crossNoteDao.update(note)
    }
    suspend fun delete(note: CrossNote){
        crossNoteDao.delete(note)
    }
    suspend fun updateFavorite(id: Long, favorite: Boolean){
        crossNoteDao.updateFavorite(id, favorite)
    }
    suspend fun updateTrash(id: Long, trash: Boolean){
        crossNoteDao.updateTrash(id, trash)
    }
}