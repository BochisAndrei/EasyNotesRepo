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

    @Query("SELECT * from cross_note_table ORDER BY cross_note_id ASC")
    fun getOrderedCrossNotes(): LiveData<List<CrossNote>>

    @Query("DELETE FROM cross_note_table")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM cross_note_table")
    fun getCheckBoxes(): LiveData<List<CrossNoteWithCheckBoxes>>

}