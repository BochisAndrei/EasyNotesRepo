package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.packg.easynotes.Elements.Folder

@Dao
interface FolderDao {
    @Insert
    suspend fun insert(folder: Folder)

    @Update
    suspend fun update(folder: Folder)

    @Delete
    suspend fun delete(folder: Folder)

    @Query("SELECT * from folder_table ORDER BY id ASC")
    fun getOrderedFolderNotes(): LiveData<List<Folder>>

    @Query("DELETE FROM folder_table")
    suspend fun deleteAll()
}