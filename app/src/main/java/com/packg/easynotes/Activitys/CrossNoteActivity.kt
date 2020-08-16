package com.packg.easynotes.Activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.packg.easynotes.DrawerFragments.AddCheckBoxDialogFragment
import com.packg.easynotes.DrawerFragments.RVAdapterCrossNote
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.ExtraReply
import com.packg.easynotes.Elements.RecyclerList
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel

class CrossNoteActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var noteViewModel: NoteViewModel
    private val dialogRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_note)

        title = findViewById(R.id.cross_note_activity_title)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val buttonSave = findViewById<ImageView>(R.id.cross_note_activity_btn_save)
        val buttonBack = findViewById<ImageView>(R.id.cross_note_activity_btn_back)
        val dialogButton = findViewById<FloatingActionButton>(R.id.cross_note_activity_floating_button)

        val intentFrom = intent
        if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
            title.setText(intentFrom.getStringExtra(ExtraReply.REPLY_TITLE))
        }

        dialogButton.setOnClickListener {
            val bundle = Bundle()
            if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
                bundle.putString("ID", intentFrom.getStringExtra(ExtraReply.REPLY_TITLE))
            }else{
                bundle.putString("ID", "1")
            }
            val checkBoxDialog = AddCheckBoxDialogFragment()
            checkBoxDialog.arguments = bundle
            checkBoxDialog.show(supportFragmentManager, "CheckBox dialog")
        }

        val recyclerView = this.findViewById<RecyclerView>(R.id.cross_note_recycler_view)
        var rvCrossNoteAdapter = RVAdapterCrossNote(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = rvCrossNoteAdapter

        val checkBoxList = ArrayList<CheckBoxNote>()
        checkBoxList.add(CheckBoxNote("ch1",true))
        checkBoxList.add(CheckBoxNote("ch2",false))
        checkBoxList.add(CheckBoxNote("ch3",true))
        rvCrossNoteAdapter.setNotes(checkBoxList)

        buttonBack.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            finish()
        }

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(title.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val titleText = title.text.toString()
                val note = CrossNote(titleText)
                if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
                    note.id = intentFrom.getLongExtra(ExtraReply.REPLY_ID,1)
                    noteViewModel.update(note)
                }else{
                    noteViewModel.insert(note, checkBoxList)
                }
                Toast.makeText(
                    this,
                    "Saved!",
                    Toast.LENGTH_SHORT).show()
            }
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

}
