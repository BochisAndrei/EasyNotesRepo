package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R


class RVAdapterCalendar(var context: Context, var arrayList: ArrayList<Event>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view : View

        view = LayoutInflater.from(context).inflate(R.layout.calendar_events, parent, false)
        return EventViewHolder(view)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var type : Event
        type = arrayList[position]
        (holder as EventViewHolder)
            .initializeUIComponents(type.text)
    }

    //class for Folder card_view
    inner class EventViewHolder(myView: View) : RecyclerView.ViewHolder(myView){
        var event_title = myView.findViewById<TextView>(R.id.calendar_events_event)

        fun initializeUIComponents(fName : String){
            var name = fName
            if(fName.length > 14)
                name = fName.substring(0,14) + "..."
            event_title.text = name

        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }


}