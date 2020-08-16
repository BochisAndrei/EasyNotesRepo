package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R


class RVAdapterCrossNote(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrayList = emptyList<CheckBoxNote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view : View

        view = LayoutInflater.from(context).inflate(R.layout.check_box_view, parent, false)
        return CheckBoxViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    public fun setNotes(list: List<CheckBoxNote>){
        this.arrayList = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var type : CheckBoxNote
        type = arrayList[position]
        (holder as CheckBoxViewHolder)
            .initializeUIComponents(type.name, type.checked)
    }

    //class for Folder card_view
    inner class CheckBoxViewHolder(myView: View) : RecyclerView.ViewHolder(myView){
        var event_title = myView.findViewById<TextView>(R.id.checkbox_view_title)
        var check_box = myView.findViewById<android.widget.CheckBox>(R.id.checkbox_view_checkbox)

        fun initializeUIComponents(title: String, checked: Boolean){
            check_box.isChecked = checked
            var name = title
            if(title.length > 20)
                name = title.substring(0,20) + "..."
            event_title.text = name

        }
    }


}