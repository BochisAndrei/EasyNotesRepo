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
import kotlinx.android.synthetic.main.sign_up_layout.*


class Sign_Up_layout : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var mCallbackManager : CallbackManager
    private lateinit var loginButton : LoginButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private var sharedP = "SHARED_USER"
    private val TAG = "FacebookAuth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        //verify if user is already logged
        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser != null) finish()

        var userName = findViewById<EditText>(R.id.sign_up_layout_edtName)
        var email = findViewById<EditText>(R.id.sign_up_layout_edtEmail)
        var pass = findViewById<EditText>(R.id.sign_up_layout_edtPassword)
        var passConfirm = findViewById<EditText>(R.id.sign_up_layout_edtConfirmPassword)
        var signUpBtn = findViewById<ImageView>(R.id.sign_up_layout_btnSignUp)
        var error = findViewById<TextView>(R.id.sign_up_layout_errorSignUp)
        var googleSignIn = findViewById<ImageView>(R.id.sign_up_layout_btnGoogleLogin)

        //--------------------------Facebook
        FacebookSdk.sdkInitialize(applicationContext)
        loginButton = findViewById(R.id.sign_up_layout_btnFacebookLogin)
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
        //--------------------------Facebook

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //sign up with google
        googleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 101)
        }

        //sign up with email and password
        signUpBtn.setOnClickListener {
            if(userName.text.toString().isEmpty()) error.text = "You need to specify your name!"
            else if(email.text.toString().isEmpty()) error.text = "You need to specify your email address!"
            else if(pass.text.toString().isEmpty()) error.text = "Please specify your password"
            else if(pass.text.toString() != passConfirm.text.toString()) error.text ="Your confirmation password does not corespond with your password!"
            else if(pass.text.toString().length < 6 || pass.text.toString().length > 30) error.text = "Your password must be between 6 and 30 characters"
            else {
                error.text = ""

                //register the user in firebase
                firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        Toast.makeText(this@Sign_Up_layout, "User Created", Toast.LENGTH_SHORT).show()
                        saveUser(userName.text.toString(), email.text.toString(), "")
                        //startMain Activity
                        startActivity(Intent(this@Sign_Up_layout, MainActivity::class.java))
                        finish()
                    }else {
                        Toast.makeText(this@Sign_Up_layout, "Error" + task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        sign_up_layout_btnLogin.setOnClickListener {
            var loginLayoutIntent = Intent(this@Sign_Up_layout, Login_layout::class.java)
            startActivity(loginLayoutIntent)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            finish()

        }
        sign_up_toolbar_btnBack.setOnClickListener {
            var mainIntent = Intent(this@Sign_Up_layout, MainActivity::class.java)
            startActivity(mainIntent)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            finish()

        }

    }

    override fun onBackPressed() {
        var backToMainActivity = Intent(this@Sign_Up_layout, MainActivity::class.java)
        startActivity(backToMainActivity)
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        finish()

    }

    //function that saves user to sharedPreferences
    fun saveUser(username : String, email : String, img: String){
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
                Toast.makeText(this@Sign_Up_layout, "Authentication failed", Toast.LENGTH_SHORT).show()
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
            startActivity(Intent(this@Sign_Up_layout, MainActivity::class.java))
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }

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
                    Toast.makeText(this@Sign_Up_layout, "User logged!", Toast.LENGTH_SHORT).show()
                    var userName = account.displayName
                    var email = account.email
                    var img = account.photoUrl
                    saveUser(userName.toString(), email.toString(),img.toString())
                    startActivity(Intent(this@Sign_Up_layout, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Sign_Up_layout, "Failed to log user!", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@Sign_Up_layout, "Failed to log user!", Toast.LENGTH_LONG).show()
            }
        }else{
            //If not request code is 101 it must be facebook
            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }
}
