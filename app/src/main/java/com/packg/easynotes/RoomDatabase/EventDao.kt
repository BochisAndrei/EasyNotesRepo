package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.packg.easynotes.Elements.Event

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event : Event)

    @Update
    suspend fun update(event : Event)

    @Delete
    suspend fun delete(event : Event)

    @Query("SELECT * from event_table ORDER BY id ASC")
    fun getOrderedEventNotes(): LiveData<List<Event>>

    @Query("DELETE FROM event_table")
    suspend fun deleteAll()
}