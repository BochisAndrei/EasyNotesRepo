package com.packg.easynotes.Dialogs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.packg.easynotes.R
import com.packg.easynotes.Singleton.DocumentManager
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class DialogFragmentChangeNavHeader: DialogFragment() {

    private var sharedP = "SHARED_USER"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.dialog_change_nav_header, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.findViewById<RelativeLayout>(R.id.drawer_first_choice_relative_layout).clipToOutline = true
        view.findViewById<RelativeLayout>(R.id.drawer_second_choice_relative_layout).clipToOutline = true
        view.findViewById<RelativeLayout>(R.id.drawer_third_choice_relative_layout).clipToOutline = true

        setImageAndUserName(view)

        var switch1 = view.findViewById<Switch>(R.id.drawer_first_choice_switch)
        var switch2 = view.findViewById<Switch>(R.id.drawer_second_choice_switch)
        var switch3 = view.findViewById<Switch>(R.id.drawer_third_choice_switch)

        var sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()
        val type = DocumentManager.getInstance().user.font
        if(type == "Normal1")
            switch1.isChecked = true
        else if(type == "Normal2")
            switch2.isChecked = true
        else if(type == "Normal3")
            switch3.isChecked = true

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked == true){
                switch2.isChecked = false
                switch3.isChecked = false
                editor.putString("FONT", "Normal1")
                DocumentManager.getInstance().user.font = "Normal1"
                editor.apply()
            }
        }

        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked == true){
                switch2.clearFocus()
                switch2.isChecked = true
                switch1.isChecked = false
                switch3.isChecked = false
                editor.putString("FONT", "Normal2")
                DocumentManager.getInstance().user.font = "Normal2"
                editor.apply()
            }
        }

        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked == true)
                switch1.isChecked = false
                switch2.isChecked = false
            editor.putString("FONT", "Normal3")
            DocumentManager.getInstance().user.font = "Normal3"
            editor.apply()
        }


        return view
    }

    private fun setImageAndUserName(view: View){
        val image = DocumentManager.getInstance().user.image
        Picasso.get()
            .load(image)
            .transform(CropCircleTransformation())
            .into(view.findViewById<ImageView>(R.id.drawer_first_choice_userImage))
        view.findViewById<TextView>(R.id.drawer_first_choice_userName).text = DocumentManager.getInstance().user.userName
        Picasso.get()
            .load(image)
            .transform(CropCircleTransformation())
            .into(view.findViewById<ImageView>(R.id.drawer_second_choice_userImage))
        view.findViewById<TextView>(R.id.drawer_second_choice_userName).text = DocumentManager.getInstance().user.userName
        Picasso.get()
            .load(image)
            .transform(CropCircleTransformation())
            .into(view.findViewById<ImageView>(R.id.drawer_third_choice_userImage))
        view.findViewById<TextView>(R.id.drawer_third_choice_userName).text = DocumentManager.getInstance().user.userName
    }
}