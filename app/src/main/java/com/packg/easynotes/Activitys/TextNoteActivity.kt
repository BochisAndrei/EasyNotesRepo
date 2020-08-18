package com.packg.easynotes.Activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.packg.easynotes.Elements.ExtraReply
import com.packg.easynotes.Elements.TextNote
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R
import com.packg.easynotes.RoomDatabase.NoteViewModel
import java.util.*

class TextNoteActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_note)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        title = findViewById(R.id.text_note_activity_title)
        description = findViewById(R.id.text_note_activity_description)

        val buttonSave = findViewById<ImageView>(R.id.text_note_activity_btnSave)
        val buttonBack = findViewById<ImageView>(R.id.text_note_activity_btn_back)

        val intentFrom = intent
        if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
            title.setText(intentFrom.getStringExtra(ExtraReply.REPLY_TITLE))
            description.setText(intentFrom.getStringExtra(ExtraReply.REPLY_DESCRIPTION))
        }

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
                val descriptionText = description.text.toString()
                val note = TextNote(titleText, descriptionText)
                if(intentFrom.hasExtra(ExtraReply.REPLY_ID)){
                    note.id = intentFrom.getLongExtra(ExtraReply.REPLY_ID,1)
                    note.createDate = Calendar.getInstance().apply { timeInMillis = intentFrom.getLongExtra(ExtraReply.REPLY_CREATED, 1) }
                    note.editedDate = Calendar.getInstance()
                    noteViewModel.update(note)
                }else{
                    noteViewModel.insert(note)
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
