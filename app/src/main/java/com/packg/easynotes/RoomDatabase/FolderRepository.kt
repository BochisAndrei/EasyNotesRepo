package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.Folder

class FolderRepository(private val folderDao: FolderDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<Folder>> = folderDao.getOrderedFolderNotes()

    suspend fun insert(note: Folder) {
        folderDao.insert(note)
    }
}