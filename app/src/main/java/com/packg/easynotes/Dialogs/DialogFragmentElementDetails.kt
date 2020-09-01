package com.packg.easynotes.Dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class DialogFragmentElementDetails: DialogFragment() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.dialog_element_details, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val activity = activity as Context

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        var creation_date = view.findViewById<TextView>(R.id.dialog_element_details_creation_date)
        var edited_date = view.findViewById<TextView>(R.id.dialog_element_details_edited_date)
        var rows_nr = view.findViewById<TextView>(R.id.dialog_element_details_nr_rows)
        var switch = view.findViewById<Switch>(R.id.dialog_element_details_favorite_switch)
        var buttonDelete = view.findViewById<Button>(R.id.dialog_element_details_delete_button)


        val mArgs = arguments
        creation_date.text = mArgs!!.getString("creation")
        edited_date.text = mArgs!!.getString("edited")
        rows_nr.text = "100"
        switch.isChecked = mArgs.getString("favorite").toBoolean()

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (mArgs.getString("type") == "textnote") {
                noteViewModel.updateFavoriteTextNote(
                    mArgs.getString("ID").toLongOrNull()!!,
                    isChecked
                )
            } else if (mArgs.getString("type") == "crossnote") {
                noteViewModel.updateFavoriteCrossNote(
                    mArgs.getString("ID").toLongOrNull()!!,
                    isChecked
                )
            }
        }

        buttonDelete.setOnClickListener {
            if (mArgs.getString("type") == "textnote") {
                noteViewModel.updateTrashTextNote(
                    mArgs.getString("ID").toLongOrNull()!!,
                    true
                )
                Toast.makeText(activity, "Note moved into trash", Toast.LENGTH_SHORT).show()
            } else if (mArgs.getString("type") == "crossnote") {
                noteViewModel.updateTrashCrossNote(
                    mArgs.getString("ID").toLongOrNull()!!,
                    true
                )
                Toast.makeText(activity, "Note moved into trash", Toast.LENGTH_SHORT).show()
            }
            dialog?.dismiss()
        }

        return view
    }
}