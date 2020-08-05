package com.packg.easynotes.Activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.packg.easynotes.R

class TextNoteActivity : AppCompatActivity() {

    private lateinit var title: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_note)

        title = findViewById(R.id.text_note_activity_title)

        val buttonSave = findViewById<ImageView>(R.id.text_note_activity_btnSave)

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(title.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = title.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }

}
