package com.packg.easynotes.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.packg.easynotes.Elements.CheckBoxNote

@Dao
interface CheckBoxDao {

    @Insert
    suspend fun insert(checkBoxNote: CheckBoxNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<CheckBoxNote>)

    @Update
    suspend fun update(checkBoxNote: CheckBoxNote)

    @Delete
    suspend fun delete(checkBoxNote: CheckBoxNote)

    @Query("SELECT * from check_box_table ORDER BY checkbox_id ASC")
    fun getOrderedCheckBox(): LiveData<List<CheckBoxNote>>

    @Query("DELETE FROM check_box_table")
    suspend fun deleteAll()


}