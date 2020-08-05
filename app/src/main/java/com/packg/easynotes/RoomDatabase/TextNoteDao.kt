package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.packg.easynotes.Elements.TextNote

@Dao
interface TextNoteDao {

    @Insert
    suspend fun insert(note: TextNote)

    @Update
    suspend fun update(note: TextNote)

    @Delete
    suspend fun delete(note: TextNote)

    @Query("SELECT * from text_note_table ORDER BY id ASC")
    fun getOrderedNotes(): LiveData<List<TextNote>>

    @Query("DELETE FROM text_note_table")
    suspend fun deleteAll()

}