package com.packg.easynotes.Activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.packg.easynotes.Dialogs.AddCheckBoxDialogFragment
import com.packg.easynotes.DrawerFragments.RVAdapterCrossNote
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.ExtraReply
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class CrossNoteActivity : AppCompatActivity(), ISelectedData, RVAdapterCrossNote.OnCheckClickListener {

    private lateinit var title: EditText
    private lateinit var noteViewModel: NoteViewModel
    private var checkBoxList = ArrayList<CheckBoxNote>()
    var rvCrossNoteAdapter = RVAdapterCrossNote(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_note)

        title = findViewById(R.id.cross_note_activity_title)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val buttonSave = findViewById<ImageView>(R.id.cross_note_activity_btn_save)
        val buttonBack = findViewById<ImageView>(R.id.cross_note_activity_btn_back)
        val dialogButton = findViewById<FloatingActionButton>(R.id.cross_note_activity_floating_button)

        val intentFrom: Intent = intent
        if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
            title.setText(intentFrom.getStringExtra(ExtraReply.REPLY_TITLE))
            lifecycleScope.launch {
                var id = intentFrom.getLongExtra(ExtraReply.REPLY_ID,1)
                var el = noteViewModel.getCheckBoxes(id)
                checkBoxList = el as ArrayList<CheckBoxNote>
                rvCrossNoteAdapter.setNotes(el)
            }
        }

        val recyclerView = this.findViewById<RecyclerView>(R.id.cross_note_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = rvCrossNoteAdapter

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    var note = (rvCrossNoteAdapter.getNoteAt(viewHolder.adapterPosition))
                    checkBoxList.remove(note)
                    note?.let { noteViewModel.delete(it) }
                    rvCrossNoteAdapter.setNotes(checkBoxList)
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        dialogButton.setOnClickListener {
            val bundle = Bundle()
            if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
                bundle.putString("ID", intentFrom.getStringExtra(ExtraReply.REPLY_TITLE))
            }else{
                bundle.putString("ID", "1")
            }
            val checkBoxDialog =
                AddCheckBoxDialogFragment(this)
            checkBoxDialog.arguments = bundle
            checkBoxDialog.show(supportFragmentManager, "CheckBox dialog")
        }

        buttonBack.setOnClickListener {
            finish()
        }

        buttonSave.setOnClickListener {
            saveData(intentFrom)
            finish()
        }

    }

    override fun finish(){
        super.finish()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }

    override fun onSelectedData(string: String?) {
        checkBoxList.add(CheckBoxNote(string!!,false))
        rvCrossNoteAdapter.setNotes(checkBoxList)
    }

    private fun addCheckBoxInfo(note: CrossNote){
        val count = checkBoxList.count()
        when {
            count==1 -> {
                note.checkBox1Name = checkBoxList[0].name
                note.checkBox1Checked = checkBoxList[0].checked
            }
            count==2 -> {
                note.checkBox1Name = checkBoxList[0].name
                note.checkBox1Checked = checkBoxList[0].checked
                note.checkBox2Name = checkBoxList[1].name
                note.checkBox2Checked = checkBoxList[1].checked
            }
            count==3 ->{
                note.checkBox1Name = checkBoxList[0].name
                note.checkBox1Checked = checkBoxList[0].checked
                note.checkBox2Name = checkBoxList[1].name
                note.checkBox2Checked = checkBoxList[1].checked
                note.checkBox3Name = checkBoxList[2].name
                note.checkBox3Checked = checkBoxList[2].checked
            }
            count>=4 ->{
                note.checkBox1Name = checkBoxList[0].name
                note.checkBox1Checked = checkBoxList[0].checked
                note.checkBox2Name = checkBoxList[1].name
                note.checkBox2Checked = checkBoxList[1].checked
                note.checkBox3Name = checkBoxList[2].name
                note.checkBox3Checked = checkBoxList[2].checked
                note.checkBox4Name = checkBoxList[3].name
                note.checkBox4Checked = checkBoxList[3].checked
            }
        }
    }

    override fun onCheckClick(note: CheckBoxNote?, position: Int, isChecked: Boolean) {
        note!!.checked = isChecked
        checkBoxList[position] = note
    }

    private fun saveData(intentFrom: Intent){
        val replyIntent = Intent()
        if (TextUtils.isEmpty(title.text)) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val titleText = title.text.toString()
            val note = CrossNote(titleText)
            //add other info to the checkBox
            addCheckBoxInfo(note)

            if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
                note.id = intentFrom.getLongExtra(ExtraReply.REPLY_ID,1)
                note.createDate = Calendar.getInstance().apply { timeInMillis = intentFrom.getLongExtra(ExtraReply.REPLY_CREATED, 1) }
                note.editedDate = Calendar.getInstance()
                note.favorite = intentFrom.getBooleanExtra(ExtraReply.REPLY_FAVORITE, false)
                note.trash = intentFrom.getBooleanExtra(ExtraReply.REPLY_TRASH, false)
                noteViewModel.update(note)
                noteViewModel.update(note.id, checkBoxList)
            }else{
                noteViewModel.insert(note, checkBoxList)
            }
            Toast.makeText(
                this,
                "Saved!",
                Toast.LENGTH_SHORT).show()
        }
    }

}
