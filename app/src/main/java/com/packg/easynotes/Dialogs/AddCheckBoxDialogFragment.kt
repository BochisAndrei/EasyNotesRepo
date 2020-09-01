package com.packg.easynotes.Dialogs

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.packg.easynotes.Activitys.ISelectedData
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel


class AddCheckBoxDialogFragment(var listener: ISelectedData) : DialogFragment()  {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var title: EditText

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.dialog_add_check_box, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val buttonSave = view.findViewById<Button>(R.id.dialog_add_check_box_save)
        title = view.findViewById<EditText>(R.id.dialog_add_check_box_edit_text)

        val bundle = arguments
        buttonSave.setOnClickListener {
            hideKeyboardFrom(requireActivity(), view)
            listener.onSelectedData(title.text.toString())
            dialog?.dismiss()
        }
        return view
    }

    private fun hideKeyboardFrom(
        context: Context,
        view: View
    ) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}