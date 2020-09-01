package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Activitys.OnItemClickListener
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class RVAdapterAllNotes(var context: Context, var listener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrayList = emptyList<Element>() // Cached copy of notes

    override fun getItemViewType(position: Int): Int {
        if(arrayList[position] is CrossNote)
            return 2 // 2 is Crossnote Type
        else if (arrayList[position] is TextNote)
            return 3 // 3 is TextNote Type
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view : View

        when (viewType) {
            2 -> {// 2 is Crossnote Type
                view = LayoutInflater.from(context).inflate(R.layout.all_notes_card_view_cross_note, parent, false)
                return CrossNoteViewHolder(view)
            }
            else -> {// 3 is TextNote Type
                view = LayoutInflater.from(context).inflate(R.layout.all_notes_card_view_text_note, parent, false)
                return TextNoteViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    internal fun setNotes(notes: List<Element>) {
        this.arrayList = notes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var type : Element
        when (holder.getItemViewType()){
            2 -> {// 2 is Crossnote Type
                type = arrayList[position] as CrossNote
                (holder as CrossNoteViewHolder)
                    .initializeUIComponents(type.name, type.checkBox1Name, type.checkBox1Checked, type.checkBox2Name, type.checkBox2Checked)
                holder.itemView.setOnClickListener{
                    listener?.onItemClick(type)
                }
                holder.itemView.setOnLongClickListener {
                    listener?.onLongItemClick(type)
                    true
                }

            }
            3 -> {// 3 is TextNote Type
                type = arrayList[position] as TextNote
                (holder as TextNoteViewHolder)
                    .initializeUIComponents(type.name, type.text)
                holder.itemView.setOnClickListener{
                    listener?.onItemClick(type)
                }
                holder.itemView.setOnLongClickListener {
                    listener?.onLongItemClick(type)
                    true
                }

            }
        }
    }

    // class for CrossNote card_view
    inner class CrossNoteViewHolder(myView: View) : RecyclerView.ViewHolder(myView){

        var crossnote_title = myView.findViewById<TextView>(R.id.all_notes_card_view_cross_note_title)
        var checkBox1 = myView.findViewById<CheckBox>(R.id.all_notes_card_view_cross_note_checkBox1)
        var checkBox2 = myView.findViewById<CheckBox>(R.id.all_notes_card_view_cross_note_checkBox2)

        fun initializeUIComponents(cName : String, cb1_name: String, cb1_checked: Boolean, cb2_name: String, cb2_checked: Boolean){
            var name = cName
            if(cName.length > 20)
                name = cName.substring(0,20) + "..."
            crossnote_title.text = name
            checkBox1.text = cb1_name
            checkBox2.text = cb2_name
            checkBox1.isChecked = cb1_checked
            checkBox2.isChecked = cb2_checked
        }
    }

    //class for Text Note card_view
    inner class TextNoteViewHolder(myView: View) : RecyclerView.ViewHolder(myView){

        var text_note_title = myView.findViewById<TextView>(R.id.all_notes_card_view_text_note_title)
        var text_note_content = myView.findViewById<TextView>(R.id.all_notes_card_view_text_note_content)

        fun initializeUIComponents(fName : String, text : String){
            var name = fName
            if(fName.length > 12)
                name = fName.substring(0,12) + "..."
            text_note_title.text = name
            text_note_content.text = text
        }
    }


}