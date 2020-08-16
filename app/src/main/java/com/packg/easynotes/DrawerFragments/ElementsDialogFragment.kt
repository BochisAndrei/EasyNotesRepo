package com.packg.easynotes.DrawerFragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.packg.easynotes.Activitys.CrossNoteActivity
import com.packg.easynotes.Activitys.TextNoteActivity
import com.packg.easynotes.Elements.ExtraReply
import com.packg.easynotes.R

class ElementsDialogFragment : DialogFragment() {

    private val activityRequestCode = 1

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.dialog_add_element_layout, container,false)

        //set background transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //buttons
        val buttonFolder = view.findViewById<Button>(R.id.dialog_add_folder)
        val buttonTextNote = view.findViewById<Button>(R.id.dialog_add_text_note)
        val buttonCrossNote = view.findViewById<Button>(R.id.dialog_add_cross_note)
        val buttonAudio = view.findViewById<Button>(R.id.dialog_add_audio)
        val buttonImage = view.findViewById<Button>(R.id.dialog_add_image)

        buttonFolder.setOnClickListener(){
            val dialogAddFolder = AddFolderDialogFragment()
            dialogAddFolder.setTargetFragment(this, activityRequestCode)
            fragmentManager?.let { it1 -> dialogAddFolder.show(it1, "AddFolderDialog") }
        }

        buttonTextNote.setOnClickListener(){
            val intent = Intent(activity, TextNoteActivity::class.java)
            startActivityForResult(intent, activityRequestCode)
        }

        buttonCrossNote.setOnClickListener(){
            val intent = Intent(activity, CrossNoteActivity::class.java)
            startActivityForResult(intent, activityRequestCode)
        }

        buttonAudio.setOnClickListener(){
            dialog?.dismiss()
        }

        buttonImage.setOnClickListener(){
            dialog?.dismiss()
        }

        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == activityRequestCode && resultCode == Activity.RESULT_OK) {
            dialog?.dismiss()
        }
        else {
            dialog?.dismiss()
        }
    }
}