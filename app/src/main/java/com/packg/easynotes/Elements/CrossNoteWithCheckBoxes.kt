package com.packg.easynotes.Elements

import androidx.room.Embedded
import androidx.room.Relation

data class CrossNoteWithCheckBoxes (
    @Embedded val crossNote: CrossNote,

    @Relation(parentColumn = "cross_note_id", entityColumn = "parent_id")
    val checkBoxes: List<CheckBoxNote> = emptyList()
)