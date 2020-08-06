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

    private val newFolderActivityRequestCode = 1
    private val newTextNoteActivityRequestCode = 2
    private val newCrossNoteActivityRequestCode = 3
    private val newAudioActivityRequestCode = 4
    private val newImageActivityRequestCode = 5



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

        }

        buttonTextNote.setOnClickListener(){
            val intent = Intent(activity, TextNoteActivity::class.java)
            startActivityForResult(intent, newTextNoteActivityRequestCode)
        }

        buttonCrossNote.setOnClickListener(){
            val intent = Intent(activity, CrossNoteActivity::class.java)
            startActivityForResult(intent, newCrossNoteActivityRequestCode)
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

        //case new Folder
        if (requestCode == newFolderActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(ExtraReply.REPLY_TITLE)
            val description = data?.getStringExtra(ExtraReply.REPLY_DESCRIPTION)

            val i: Intent = Intent()
                .putExtra(ExtraReply.REPLY_TITLE, title)
                .putExtra(ExtraReply.REPLY_DESCRIPTION, description)
                .putExtra(ExtraReply.REPLY_TYPE, 1)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, i)
            dialog?.dismiss()

        }
        //case new TextNote
        else if (requestCode == newTextNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(ExtraReply.REPLY_TITLE)
            val description = data?.getStringExtra(ExtraReply.REPLY_DESCRIPTION)

            val i: Intent = Intent()
                .putExtra(ExtraReply.REPLY_TITLE, title)
                .putExtra(ExtraReply.REPLY_DESCRIPTION, description)
                .putExtra(ExtraReply.REPLY_TYPE, 2)
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, i)
            dialog?.dismiss()

        }
        //case new CrossNote
        else if (requestCode == newCrossNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(ExtraReply.REPLY_TITLE)
            Toast.makeText(
                activity,
                "Saved " + title.toString(),
                Toast.LENGTH_LONG).show()
            dialog?.dismiss()
        }
        else {
            Toast.makeText(
                activity,
                "Not saved!",
                Toast.LENGTH_LONG).show()
            dialog?.dismiss()
        }
    }
}