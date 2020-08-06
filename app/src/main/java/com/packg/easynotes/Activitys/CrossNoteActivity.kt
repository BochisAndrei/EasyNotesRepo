package com.packg.easynotes.Activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import com.packg.easynotes.Elements.ExtraReply
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R

class CrossNoteActivity : AppCompatActivity() {

    private lateinit var title: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_note)

        title = findViewById(R.id.cross_note_activity_title)

        val buttonSave = findViewById<ImageView>(R.id.cross_note_activity_btn_save)
        val buttonBack = findViewById<ImageView>(R.id.cross_note_activity_btn_back)

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
                replyIntent.putExtra(ExtraReply.REPLY_TITLE, titleText)
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
