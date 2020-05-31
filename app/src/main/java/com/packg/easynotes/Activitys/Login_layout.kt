package com.packg.easynotes.Activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.packg.easynotes.R
import kotlinx.android.synthetic.main.login_layout.*

class Login_layout : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mCallbackManager : CallbackManager
    private lateinit var loginButton : LoginButton
    private var sharedP = "SHARED_USER"
    private val TAG = "FacebookAuth"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        firebaseAuth = FirebaseAuth.getInstance()

        //verify if an account already exist
        if(firebaseAuth.currentUser != null) {
            finish()
        }

        var userName = findViewById<EditText>(R.id.login_layout_edtName)
        var email = findViewById<EditText>(R.id.login_layout_edtEmail)
        var pass = findViewById<EditText>(R.id.login_layout_edtPassword)
        var error = findViewById<TextView>(R.id.login_layout_errorLogin)
        var googleSignIn = findViewById<ImageView>(R.id.login_layout_btnGoogleLogin)

        //Authenticate with facebook
        FacebookSdk.sdkInitialize(applicationContext)
        loginButton = findViewById(R.id.login_layout_btnFacebookLogin)
        mCallbackManager = CallbackManager.Factory.create()
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookToken(loginResult.accessToken)
            }
            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                updateUI(null)
            }
            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                updateUI(null)
            }
        })

        //initialize google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //sign in with google
        googleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 101)
        }

        //authenticate the user with email and password
        login_layout_btnLogin.setOnClickListener {
            if(userName.text.toString().isEmpty()) error.text = "You need to specify your name!"
            else if(email.text.toString().isEmpty()) error.text = "You need to specify your email address!"
            else if(pass.text.toString().isEmpty()) error.text = "Please specify your password"
            else {
                error.text = ""

                //signIn the user in firebase
                firebaseAuth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this@Login_layout, "User Logged", Toast.LENGTH_SHORT).show()
                        saveUser(userName.text.toString(), email.text.toString(),"")
                        //startMain Activity
                        startActivity(Intent(this@Login_layout, MainActivity::class.java))
                        finish()
                    }else {
                        Toast.makeText(this@Login_layout, "Error" + task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        //forgot password button
        login_layout_forgot_password.setOnClickListener {
            if (email.text.toString().isNotEmpty()) {
                error.text = "Click one more time to reset the password!"
                login_layout_forgot_password.setOnClickListener {
                    if (email.text.toString().isNotEmpty()) {
                        firebaseAuth.sendPasswordResetEmail(email.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful)
                                    Toast.makeText(this@Login_layout, "Email sent!", Toast.LENGTH_SHORT).show()
                            }
                    }else error.text = "Please enter your email!"
                }
            }else error.text = "Please enter your email!"
        }

        login_layout_btnSignup.setOnClickListener {
            var signUpIntent = Intent(this@Login_layout, Sign_Up_layout::class.java)
            startActivity(signUpIntent)
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            );
            finish()

        }
        login_toolbar_btnBack.setOnClickListener{
            var mainIntent = Intent(this@Login_layout, MainActivity::class.java)
            startActivity(mainIntent)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            finish()

        }
    }

    override fun onBackPressed() {
        var backToMainActivity = Intent(this@Login_layout, MainActivity::class.java)
        startActivity(backToMainActivity)
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        finish()

    }

    //save the user in sharedPreferences
    fun saveUser(username : String, email : String, img : String){
        var sharedPreferences : SharedPreferences = getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.putString("EMAIL", email)
        if(!img.equals("")) editor.putString("IMAGE", img)
        editor.apply()
    }

    //functions for facebook authentication
    private fun handleFacebookToken(token: AccessToken){
        Log.d(TAG, "handleFacebookToken" + token)
        var credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful){
                Log.d(TAG, "Sign in with credential: successful")
                val user = firebaseAuth.currentUser
                updateUI(user)
            }else{
                Log.d(TAG, "Sign in with credential: failure", task.exception)
                Toast.makeText(this@Login_layout, "Authentication failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }

        }
    }
    private fun updateUI(user : FirebaseUser?){
        if(user != null){
            if(user.email != null && user.photoUrl != null)
                saveUser(user.displayName.toString(), user.email.toString(), user.photoUrl.toString())
            else if (user.email == null && user.photoUrl != null)
                saveUser(user.displayName.toString(), "", user.photoUrl.toString())
            else if (user.email == null && user.photoUrl == null)
                saveUser(user.displayName.toString(), "", "")
            startActivity(Intent(this@Login_layout, MainActivity::class.java))
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }

    //function called in login_layout.xml
    public fun onClickFacebookButton(view : View) {
        loginButton.performClick()
    }

    //authenticate with google functions
    private fun firebaseAuthWithGoogle(account : GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this@Login_layout, "User logged!", Toast.LENGTH_SHORT).show()
                    var userName = account.displayName
                    var email = account.email
                    var img = account.photoUrl
                    saveUser(userName.toString(), email.toString(), img.toString())
                    startActivity(Intent(this@Login_layout, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login_layout, "Failed to log user!", Toast.LENGTH_LONG).show()
                }
            }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                //error
                Toast.makeText(this@Login_layout, "Failed to log user!", Toast.LENGTH_LONG).show()
            }
        }else{
            //If request code isn't 101 -> it must be facebook
            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }


}
