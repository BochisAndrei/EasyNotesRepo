package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.packg.easynotes.Elements.CheckBoxNote

@Dao
interface CheckBoxDao {

    @Insert
    suspend fun insert(checkBoxNote: CheckBoxNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(checkBoxNote: List<CheckBoxNote>)

    @Update
    suspend fun update(checkBoxNote: CheckBoxNote)

    @Update
    suspend fun updateAll(checkBoxNote: List<CheckBoxNote>)

    @Delete
    suspend fun delete(checkBoxNote: CheckBoxNote)

    @Query("SELECT * from check_box_table ORDER BY checkbox_id ASC")
    fun getOrderedCheckBox(): LiveData<List<CheckBoxNote>>

    @Query("DELETE FROM check_box_table")
    suspend fun deleteAll()

    @Query("SELECT * from check_box_table WHERE parent_id=:id")
    suspend fun getCheckBoxes(id: Long): List<CheckBoxNote>

}