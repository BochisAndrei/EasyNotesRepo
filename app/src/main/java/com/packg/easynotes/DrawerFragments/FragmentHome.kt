package com.packg.easynotes.DrawerFragments

import android.app.Activity
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
import com.packg.easynotes.Elements.ExtraReply
import com.packg.easynotes.Elements.TextNote
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel


class FragmentHome : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private val dialogRequestCode = 1


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
            dialog.setTargetFragment(this, dialogRequestCode)
            fragmentManager?.let { it1 -> dialog.show(it1, "ElementsDialog") }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.home_recycler_view)
        var rvHomeAdapter = RVAdapterHome(activity)
        recyclerView.layoutManager = GridLayoutManager(activity, Utility.calculateNoOfColumns(activity, 200f))
        recyclerView.adapter = rvHomeAdapter

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(this.viewLifecycleOwner, Observer { notes ->
            // Update the cached copy of the notes in the adapter.
            notes?.let { rvHomeAdapter.setNotes(it) }
        })

        return view

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == dialogRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(ExtraReply.REPLY_TITLE)
            val description = data?.getStringExtra(ExtraReply.REPLY_DESCRIPTION)
            val note = TextNote(title!!, description!!)
            noteViewModel.insert(note)
            Toast.makeText(
                activity,
                "Saved!",
                Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                activity,
                "Not saved!",
                Toast.LENGTH_LONG).show()
        }
    }

    object Utility {
        fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {
            val displayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / columnWidthDp + 0.5).toInt()
        }
    }

}