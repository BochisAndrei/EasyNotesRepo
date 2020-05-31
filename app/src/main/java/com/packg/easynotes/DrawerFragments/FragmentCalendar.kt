package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Elements.Event
import com.packg.easynotes.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FragmentCalendar : Fragment() {

    companion object {

        fun newInstance(): FragmentCalendar {
            return FragmentCalendar()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_calendar, container,false)


        var rightNow = Calendar.getInstance()
        var dateformat = SimpleDateFormat("yyyy")
        var dayFormat = SimpleDateFormat("d MMM")
        var datetime = dateformat.format(rightNow.time)
        var dayTime = dayFormat.format(rightNow.time)

        val txtHour = view.findViewById<TextView>(R.id.calendar_txtDate)
        var txtDay = view.findViewById<TextView>(R.id.calendar_txtDateDay)

        txtHour.text = datetime.toString()
        txtDay.text = dayTime.toString()

        val activity = activity as Context

        var events = ArrayList<Event>()
        var event1 = Event(null, "event1")
        var event2 = Event(null, "event1")
        events.add(event1)
        events.add(event2)

        val recyclerView = view.findViewById<RecyclerView>(R.id.calendar_events_recyler_view)
        var rvCalendarAdapter = RVAdapterCalendar(activity, events)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = rvCalendarAdapter

        return view



    }


}