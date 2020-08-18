package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.R


class RVAdapterCrossNote(var context: Context, var listener: OnCheckClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrayList = emptyList<CheckBoxNote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view : View

        view = LayoutInflater.from(context).inflate(R.layout.card_view_check_box, parent, false)
        return CheckBoxViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun setNotes(list: List<CheckBoxNote>){
        this.arrayList = list
        notifyDataSetChanged()
    }

    fun getNotes(): List<CheckBoxNote>{
        return this.arrayList
    }

    fun getNoteAt(position: Int): CheckBoxNote? {
        return arrayList[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var type : CheckBoxNote
        type = arrayList[position]
        (holder as CheckBoxViewHolder)
            .initializeUIComponents(type.name, type.checked)
        holder.check_box.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                listener.onCheckClick(type, position, true)
            else
                listener.onCheckClick(type, position, false)
        }

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

    interface OnCheckClickListener {
        fun onCheckClick(note: CheckBoxNote?, position: Int, isChecked: Boolean)
    }
}