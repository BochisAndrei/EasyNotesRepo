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

    @Query("SELECT * from text_note_table WHERE trash = 0 ORDER BY id ASC ")
    fun getOrderedNotes(): LiveData<List<TextNote>>

    @Query("SELECT * from text_note_table WHERE favorite = 1 ORDER BY id ASC ")
    fun getOrderedFavoriteNotes(): LiveData<List<TextNote>>

    @Query("SELECT * from text_note_table WHERE trash = 1 ORDER BY id ASC ")
    fun getOrderedTrashedNotes(): LiveData<List<TextNote>>

    @Query("DELETE FROM text_note_table")
    suspend fun deleteAll()

    @Query("UPDATE text_note_table SET favorite = :favorite WHERE id = :id AND trash = 0")
    fun updateFavorite(id: Long, favorite: Boolean?)

    @Query("UPDATE text_note_table SET trash = :trash WHERE id = :id")
    fun updateTrash(id: Long, trash: Boolean?)
}