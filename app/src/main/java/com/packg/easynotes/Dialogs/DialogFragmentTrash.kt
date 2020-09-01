package com.packg.easynotes.Dialogs

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.packg.easynotes.Activitys.CrossNoteActivity
import com.packg.easynotes.Activitys.TextNoteActivity
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.TextNote
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class DialogFragmentTrash : DialogFragment() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.dialog_trash_screen, container,false)

        //set background transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        //buttons
        val buttonSendNoteBack = view.findViewById<Button>(R.id.dialog_send_note_back)
        val buttonRemoveNote = view.findViewById<Button>(R.id.dialog_remove_note)

        val mArgs = arguments
        buttonSendNoteBack.setOnClickListener{
            if (mArgs!!.getString("type") == "textnote") {
                noteViewModel.updateTrashTextNote(mArgs.getString("ID").toLongOrNull()!!, false)
            } else if (mArgs.getString("type") == "crossnote") {
                noteViewModel.updateTrashCrossNote(mArgs.getString("ID").toLongOrNull()!!, false)
            }
            dialog?.dismiss()
        }

        buttonRemoveNote.setOnClickListener{
            if (mArgs!!.getString("type") == "textnote") {
                var note = TextNote("", "")
                note.id = mArgs.getString("ID").toLongOrNull()!!
                noteViewModel.delete(note)
            } else if (mArgs.getString("type") == "crossnote") {
                var note = CrossNote("")
                note.id = mArgs.getString("ID").toLongOrNull()!!
                noteViewModel.delete(note)
            }
            dialog?.dismiss()
        }

        return view
    }
}