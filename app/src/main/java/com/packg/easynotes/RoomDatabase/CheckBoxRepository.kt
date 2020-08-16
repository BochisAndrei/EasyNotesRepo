package com.packg.easynotes.RoomDatabase

import android.widget.CheckBox
import androidx.lifecycle.LiveData
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.CrossNoteWithCheckBoxes

class CheckBoxRepository(private val checkBoxDao: CheckBoxDao)  {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allCheckBoxNotes: LiveData<List<CheckBoxNote>> = checkBoxDao.getOrderedCheckBox()

    suspend fun insert(note: CheckBoxNote) {
        checkBoxDao.insert(note)
    }
    suspend fun insertAll(note: List<CheckBoxNote>){
        checkBoxDao.insertAll(note)
    }
    suspend fun update(note: CheckBoxNote){
        checkBoxDao.update(note)
    }
}