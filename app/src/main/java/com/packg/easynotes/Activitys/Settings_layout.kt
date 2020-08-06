package com.packg.easynotes.Activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R
import com.packg.easynotes.Singleton.DocumentManager
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.settings_layout.*

class Settings_layout : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private var sharedP = "SHARED_USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)
        //initialize google account for signing out
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        var user_name = findViewById<TextView>(R.id.account_settings_user_name)
        user_name.text = DocumentManager.getInstance().user.userName
        Picasso.get()
            .load(DocumentManager.getInstance().user.image)
            .transform(CropCircleTransformation())
            .into(findViewById<ImageView>(R.id.account_settings_userImage))

        settings_layout_toolbar_btnBack.setOnClickListener {
            var intent = Intent(this@Settings_layout, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            finish()

        }
    }

    override fun onBackPressed() {
        var intent = Intent(this@Settings_layout, MainActivity::class.java)
        startActivity(Intent(intent))
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        finish()
    }

    fun logout(view : View){
        FirebaseAuth.getInstance().signOut() //sign out from firebase
        googleSignInClient.signOut() //sign out and give the possibility to chose another account
        FacebookSdk.sdkInitialize(getApplicationContext())
        LoginManager.getInstance().logOut()
        AccessToken.setCurrentAccessToken(null)
        var sharedPreferences : SharedPreferences = getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("USERNAME","")
        editor.putString("EMAIL", "")
        editor.putString("IMAGE", "")
        editor.commit()
        startActivity(Intent(this@Settings_layout, MainActivity::class.java))
        finish()
    }

}
