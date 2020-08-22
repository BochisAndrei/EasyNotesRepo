package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.packg.easynotes.Activitys.ISelectedData
import com.packg.easynotes.R

class DialogFragmentChangeWaterLimit : DialogFragment()  {

    private var sharedP = "SHARED_USER"
    lateinit var listener: ISelectedData
    private lateinit var numberPickerPriority: NumberPicker

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.dialog_change_water_limit, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val buttonSave = view.findViewById<Button>(R.id.dialog_change_water_limit_save)

        val min = 250
        val max = 4000
        val step = 250

        val list = ArrayList<String>()
        for(i in min until max step min){
            list.add("$i ml")
        }

        val values = arrayOfNulls<String>(list.size)
        list.toArray(values)

        numberPickerPriority = view.findViewById(R.id.dialog_change_water_number_picker)
        numberPickerPriority.minValue = 0
        numberPickerPriority.maxValue = values.size - 1
        numberPickerPriority.displayedValues = values

        var sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()

        buttonSave.setOnClickListener {
            val value = numberPickerPriority.value
            listener.onSelectedData(list[value])
            dialog?.dismiss()
        }

        return view
    }

    fun addListener(listener: ISelectedData){
        this.listener = listener
    }
}