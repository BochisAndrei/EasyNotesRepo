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
import com.packg.easynotes.Elements.ExtraReply
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R

class TextNoteActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_note)

        title = findViewById(R.id.text_note_activity_title)
        description = findViewById(R.id.text_note_activity_description)

        val buttonSave = findViewById<ImageView>(R.id.text_note_activity_btnSave)
        val buttonBack = findViewById<ImageView>(R.id.text_note_activity_btn_back)

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
                replyIntent.putExtra(ExtraReply.REPLY_TITLE, titleText)
                replyIntent.putExtra(ExtraReply.REPLY_DESCRIPTION,descriptionText)
                setResult(Activity.RESULT_OK, replyIntent)
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
