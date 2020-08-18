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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.packg.easynotes.Activitys.CrossNoteActivity
import com.packg.easynotes.Activitys.OnItemClickListener
import com.packg.easynotes.Activitys.TextNoteActivity
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class FragmentHome : Fragment(), OnItemClickListener {

    private lateinit var noteViewModel: NoteViewModel

    companion object {
        fun newInstance(): FragmentHome {
            return FragmentHome()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container,false)
        val activity = activity as Context

        val openDialog = view.findViewById<FloatingActionButton>(R.id.home_floating_button)

        openDialog.setOnClickListener {
            val dialog = ElementsDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, "ElementsDialog")
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.home_recycler_view)
        var rvHomeAdapter = RVAdapterHome(activity, this)
        recyclerView.layoutManager = GridLayoutManager(activity, Utility.calculateNoOfColumns(activity, 200f))
        recyclerView.adapter = rvHomeAdapter

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            val list = RecyclerList.sortTextNote(notes)
            rvHomeAdapter.setNotes(list)
        })
        noteViewModel.allCrossNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            val list = RecyclerList.sortCrossNote(notes)
            rvHomeAdapter.setNotes(list)
        })
        noteViewModel.allFolderNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            val list = RecyclerList.sortFolder(notes)
            rvHomeAdapter.setNotes(list)
        })
        return view
    }

    object Utility {
        fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {
            val displayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / columnWidthDp + 0.5).toInt()
        }
    }

    override fun onItemClick(note: Element?) {
        when (note) {
            is TextNote -> {
                val intent = Intent(activity, TextNoteActivity::class.java)
                intent.putExtra(ExtraReply.REPLY_ID, note.id)
                intent.putExtra(ExtraReply.REPLY_TITLE, note.name)
                intent.putExtra(ExtraReply.REPLY_DESCRIPTION, note.text)
                intent.putExtra(ExtraReply.REPLY_CREATED, note.createDate.timeInMillis)
                startActivity(intent)
            }
            is Folder -> {
                Toast.makeText(activity, "Folder clicked", Toast.LENGTH_SHORT).show()
            }
            is CrossNote -> {
                val intent = Intent(activity, CrossNoteActivity::class.java)
                intent.putExtra(ExtraReply.REPLY_ID, note.id)
                intent.putExtra(ExtraReply.REPLY_TITLE,note.name)
                intent.putExtra(ExtraReply.REPLY_CREATED, note.createDate.timeInMillis)
                startActivity(intent)
            }
        }
    }


}