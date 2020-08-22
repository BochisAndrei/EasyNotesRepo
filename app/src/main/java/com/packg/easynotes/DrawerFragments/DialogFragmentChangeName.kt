package com.packg.easynotes.DrawerFragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.packg.easynotes.Activitys.ISelectedData
import com.packg.easynotes.Elements.Folder
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel
import com.packg.easynotes.Singleton.DocumentManager

class DialogFragmentChangeName : DialogFragment()  {

    private var sharedP = "SHARED_USER"
    lateinit var listener: ISelectedData

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.dialog_change_name, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val buttonSave = view.findViewById<Button>(R.id.dialog_change_name_save)
        val title = view.findViewById<EditText>(R.id.dialog_change_name_edit_text)

        var sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()

        buttonSave.setOnClickListener {
            editor.putString("USERNAME", title.text.toString())
            DocumentManager.getInstance().user.userName = title.text.toString()
            listener.onSelectedData(title.text.toString())
            editor.apply()
            dialog?.dismiss()
        }

        return view
    }

    fun addListener(listener: ISelectedData){
        this.listener = listener
    }
}