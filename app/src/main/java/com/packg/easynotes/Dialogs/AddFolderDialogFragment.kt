package com.packg.easynotes.Dialogs

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.packg.easynotes.Elements.Folder
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class AddFolderDialogFragment : DialogFragment()  {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.dialog_add_folder, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val buttonSave = view.findViewById<Button>(R.id.dialog_add_folder_save)
        val title = view.findViewById<EditText>(R.id.dialog_add_folder_edit_text)

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if(TextUtils.isEmpty(title.text)){
                targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, replyIntent)
                dialog?.dismiss()
            }else{
                val titleText = title.text.toString()
                val note = Folder(titleText, 1)
                noteViewModel.insert(note)
                targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, replyIntent)
                dialog?.dismiss()
            }
        }

        return view
    }
}