package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Activitys.CrossNoteActivity
import com.packg.easynotes.Activitys.OnItemClickListener
import com.packg.easynotes.Activitys.TextNoteActivity
import com.packg.easynotes.Dialogs.DialogFragmentElementDetails
import com.packg.easynotes.Dialogs.DialogFragmentTrash
import com.packg.easynotes.Elements.*
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class FragmentTrash : Fragment(), OnItemClickListener {

    private lateinit var noteViewModel: NoteViewModel

    companion object {

        fun newInstance(): FragmentTrash {
            return FragmentTrash()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_notes, container,false)
        val activity = activity as Context
        (activity as MainActivity).toolbar.findViewById<ImageView>(R.id.drawer_toolbar_search).setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.icon_search))
        (activity as MainActivity).toolbar.findViewById<ImageView>(R.id.drawer_toolbar_search).visibility = View.VISIBLE
        (activity as MainActivity).toolbar.findViewById<TextView>(R.id.main_activity_toolbar_title).text = getString(
                    R.string.FragmentTrashTitle)
        (activity as MainActivity).toolbar.setBackgroundColor(Color.WHITE)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.all_notes_recycler_view)
        var rvHomeAdapter = RVAdapterAllNotes(activity, this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = rvHomeAdapter

        noteViewModel.allTrashedNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            val list = RecyclerList.sortTextNoteForTrashedNotes(notes)
            rvHomeAdapter.setNotes(list)
        })
        noteViewModel.allTrashedCrossNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            val list = RecyclerList.sortCrossNoteForTrashedNotes(notes)
            rvHomeAdapter.setNotes(list)
        })

        return view
    }

    override fun onItemClick(note: Element?) {
        val dialog = DialogFragmentTrash()
        val args = Bundle()
        when (note) {
            is TextNote -> {
                args.putString("type", "textnote")
                args.putString("ID", note.id.toString())
                dialog.arguments = args
                dialog.show(requireActivity().supportFragmentManager, "TrashDialog")
            }
            is CrossNote -> {
                args.putString("type", "crossnote")
                args.putString("ID", note.id.toString())
                dialog.arguments = args
                dialog.show(requireActivity().supportFragmentManager, "TrashDialog")
            }
        }
    }

    override fun onLongItemClick(note: Element?) {
    }

}