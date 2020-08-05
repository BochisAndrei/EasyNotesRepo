package com.packg.easynotes.Activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MotionEventCompat
import com.packg.easynotes.Animations.RevealAnimation
import com.packg.easynotes.Animations.TypeWriter
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R
import java.util.*


class WellcomeLayout : AppCompatActivity() {
    private var sharedP = "SHARED_USER"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wellcome_layout)

        var layout = findViewById<ConstraintLayout>(R.id.wellcome_layout)
        var wellcome_message = findViewById<TypeWriter>(R.id.wellcome_layout_message)
        var view_name = findViewById<TypeWriter>(R.id.wellcome_layout_name)

        var face = Typeface.createFromAsset(assets, "fonts/great_vibes_regular.ttf")
        var sharedPreferences : SharedPreferences = getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        var userName = sharedPreferences.getString("USERNAME", "")

        var rightNow = Calendar.getInstance()
        var h = rightNow.get(Calendar.HOUR_OF_DAY)
        if(h>=4 && h<12) {
            layout.setBackground(ContextCompat.getDrawable(this,
                R.drawable.bg_good_afternoon
            ))
            wellcome_message.setTypeface(face)
            wellcome_message.setCharacterDelay(200)
            wellcome_message.animateText(" Good Morning,")
            view_name.setTypeface(face)
            view_name.setCharacterDelay(200)
            view_name.animateText(userName.toString())
        }else if(h>=12 && h<17){
            layout.setBackground(ContextCompat.getDrawable(this,
                R.drawable.bg_good_afternoon
            ))
            wellcome_message.setTypeface(face)
            wellcome_message.setCharacterDelay(200)
            wellcome_message.animateText(" Good Afternoon,")
            view_name.setTypeface(face)
            view_name.setCharacterDelay(200)
            view_name.animateText(userName.toString())
        }else if(h>=17 || h<4){
            layout.setBackground(ContextCompat.getDrawable(this,
                R.drawable.bg_good_evening
            ))
            wellcome_message.setTypeface(face)
            wellcome_message.setCharacterDelay(200)
            wellcome_message.animateText(" Good Evening,")
            view_name.setTypeface(face)
            view_name.setCharacterDelay(200)
            view_name.animateText(userName.toString())
        }



    }

    fun showActivity() {
        //ActivityOptionsCompat.makeSceneTransitionAnimation(this, this, "transition")
        val revealX = this.getResources().getDisplayMetrics().widthPixels/2
        val revealY = this.getResources().getDisplayMetrics().heightPixels/2
        val intent = Intent(this@WellcomeLayout, MainActivity::class.java)
        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, revealX)
        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, revealY)
        ActivityCompat.startActivity(this, intent, null)
        overridePendingTransition(0, 0)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action: Int = MotionEventCompat.getActionMasked(event)

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                true
            }
            MotionEvent.ACTION_MOVE -> {
                true
            }
            MotionEvent.ACTION_UP -> {
                showActivity()
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}
