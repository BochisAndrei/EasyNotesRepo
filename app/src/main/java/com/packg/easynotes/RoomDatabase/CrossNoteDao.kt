package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.CrossNoteWithCheckBoxes
import com.packg.easynotes.Elements.Element

@Dao
interface CrossNoteDao {
    @Insert
    suspend fun insert(crossNote : CrossNote) : Long

    @Update
    suspend fun update(crossNote : CrossNote)

    @Delete
    suspend fun delete(crossNote : CrossNote)

    @Query("SELECT * from cross_note_table WHERE trash = 0 ORDER BY cross_note_id ASC")
    fun getOrderedCrossNotes(): LiveData<List<CrossNote>>

    @Query("SELECT * from cross_note_table WHERE favorite = 1 ORDER BY cross_note_id ASC")
    fun getOrderedFavoriteCrossNotes(): LiveData<List<CrossNote>>

    @Query("SELECT * from cross_note_table WHERE trash = 1 ORDER BY cross_note_id ASC")
    fun getOrderedTrashedCrossNotes(): LiveData<List<CrossNote>>

    @Query("DELETE FROM cross_note_table")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM cross_note_table")
    fun getCheckBoxes(): LiveData<List<CrossNoteWithCheckBoxes>>

    @Query("UPDATE cross_note_table SET favorite = :favorite WHERE cross_note_id = :id")
    fun updateFavorite(id: Long, favorite: Boolean?)

    @Query("UPDATE cross_note_table SET trash = :trash WHERE cross_note_id = :id")
    fun updateTrash(id: Long, trash: Boolean?)
}