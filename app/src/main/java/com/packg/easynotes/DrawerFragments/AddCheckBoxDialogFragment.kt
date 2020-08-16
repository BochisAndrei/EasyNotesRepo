package com.packg.easynotes.DrawerFragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.Elements.RecyclerList
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class AddCheckBoxDialogFragment : DialogFragment()  {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.dialog_add_check_box, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val buttonSave = view.findViewById<Button>(R.id.dialog_add_check_box_save)
        val title = view.findViewById<EditText>(R.id.dialog_add_check_box_edit_text)

        val bundle = arguments
        val id = bundle!!.getString("ID", "nope")
        buttonSave.setOnClickListener {
            var note = CheckBoxNote(id,true)
            //RecyclerList.addCheckBox(note)
            dialog?.dismiss()
        }
        return view
    }
}