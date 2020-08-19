package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Activitys.CrossNoteActivity
import com.packg.easynotes.Activitys.OnItemClickListener
import com.packg.easynotes.Activitys.TextNoteActivity
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class FragmentAllNotes : Fragment(), OnItemClickListener {

    private lateinit var noteViewModel: NoteViewModel

    companion object {

        fun newInstance(): FragmentAllNotes {
            return FragmentAllNotes()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_notes, container,false)
        val activity = activity as Context

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.all_notes_recycler_view)
        var rvHomeAdapter = RVAdapterAllNotes(activity, this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = rvHomeAdapter

        noteViewModel.allNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            val list = RecyclerList.sortTextNoteForAllNotes(notes)
            rvHomeAdapter.setNotes(list)
        })
        noteViewModel.allCrossNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            val list = RecyclerList.sortCrossNoteForAllNotes(notes)
            rvHomeAdapter.setNotes(list)
        })

        return view
    }

    override fun onItemClick(note: Element?) {
        when (note) {
            is TextNote -> {
                val intent = Intent(activity, TextNoteActivity::class.java)
                intent.putExtra(ExtraReply.REPLY_ID, note.id)
                intent.putExtra(ExtraReply.REPLY_TITLE, note.name)
                intent.putExtra(ExtraReply.REPLY_DESCRIPTION, note.text)
                intent.putExtra(ExtraReply.REPLY_FAVORITE,note.favorite)
                intent.putExtra(ExtraReply.REPLY_TRASH, note.trash)
                intent.putExtra(ExtraReply.REPLY_CREATED, note.createDate.timeInMillis)
                startActivity(intent)
            }
            is CrossNote -> {
                val intent = Intent(activity, CrossNoteActivity::class.java)
                intent.putExtra(ExtraReply.REPLY_ID, note.id)
                intent.putExtra(ExtraReply.REPLY_TITLE, note.name)
                intent.putExtra(ExtraReply.REPLY_FAVORITE,note.favorite)
                intent.putExtra(ExtraReply.REPLY_TRASH, note.trash)
                intent.putExtra(ExtraReply.REPLY_CREATED, note.createDate.timeInMillis)
                startActivity(intent)
            }
        }
    }

    override fun onLongItemClick(note: Element?) {
        TODO("Not yet implemented")
    }


}