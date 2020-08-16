package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.CrossNoteWithCheckBoxes

class CrossNoteRepository(private val crossNoteDao: CrossNoteDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<CrossNote>> = crossNoteDao.getOrderedCrossNotes()
    val crossNoteAndCheckBoxNote: LiveData<List<CrossNoteWithCheckBoxes>> = crossNoteDao.getCheckBoxes()

    suspend fun getCheckBoxes(id: Long): LiveData<List<CrossNoteWithCheckBoxes>>{
        return crossNoteDao.getCheckBoxes(id)
    }
    suspend fun insert(note: CrossNote) : Long{
        return crossNoteDao.insert(note)
    }
    suspend fun update(note: CrossNote){
        crossNoteDao.update(note)
    }
}