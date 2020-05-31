package com.packg.easynotes.Activitys

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.packg.easynotes.Animations.RevealAnimation
import com.packg.easynotes.DrawerFragments.*
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R
import com.packg.easynotes.SharedPArray.DataConfig
import com.packg.easynotes.SharedPArray.PrefConfigNotesArray
import com.packg.easynotes.Singleton.DocumentManager
import com.packg.easynotes.User
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout : DrawerLayout
    lateinit var actionBarToggle : ActionBarDrawerToggle
    lateinit var navigationView : NavigationView
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    private var sharedP = "SHARED_USER"
    lateinit var mRevealAnimation : RevealAnimation
    lateinit var addElementDialog : Dialog
    lateinit var addEventDialog : Dialog
    var TIME_DELAY = 2000
    var back_pressed = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //open activity with circular reveal animation
        if(savedInstanceState == null)
            revealAnimation(this.intent)

        //Add the user and drawer to the app:
        val user = loadUser()
        setupDrawer(user)
        //initialize the notes
        initializeNotes()

        val authenticationBtn = navigationView.getHeaderView(0).findViewById<TextView>(
            R.id.drawer_authentication
        )
        val accountSettingsBtn = navigationView.getHeaderView(0).findViewById<TextView>(
            R.id.drawer_logged_accountSettings
        )

        // if account dosen't exist the login activity will open if clicked
        if (authenticationBtn != null)
            openLogin(authenticationBtn)
        else if(accountSettingsBtn != null)
            openAccountSettings(accountSettingsBtn)

        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container_fragment,
                    FragmentHome()
                )
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

    }
    private fun testArray(): ArrayList<Element> {
        var note = TextNote(2,"Welcome","This is a Textnote!")
        var note1 = Audio(3,"@drawable/bg_good_evening")
        var corss = CrossNote(4, "CrossNote")
        var arrayList2 = ArrayList<Element>()
        arrayList2.add(note)
        arrayList2.add(note)
        var folder1 = Folder(1,"Folder1",
            R.drawable.bg_good_evening
        )
        folder1.elementsList = arrayList2
        var arrayList : ArrayList<Element> = ArrayList()
        arrayList.add(note)
        arrayList.add(note1)
        arrayList.add(corss)
        arrayList.add(folder1)
        return arrayList
    }

    //function to initialize notes that are show to the home screen
    private fun initializeNotes() {
        if(!PrefConfigNotesArray.arrayIsEmpty(this)) {
            PrefConfigNotesArray.writeListInPref(this, testArray())
            DocumentManager.getInstance().list = PrefConfigNotesArray.readListFromPref(this)
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container_fragment,
                    FragmentHome()
                )
                .commit()
        }else{

        }
    }
    fun initializeEvents(){
        if(!PrefConfigNotesArray.arrayEventIsEmpty(this))
            DocumentManager.getInstance().events = PrefConfigNotesArray.readEventListFromPref(this)
        else {
            var event = Event(null,"title")
            DocumentManager.getInstance().addEvent(event, this)
            DocumentManager.getInstance().addEvent(event, this)
        }

    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            if(back_pressed + TIME_DELAY > System.currentTimeMillis()){
                var intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Press again to exit" , Toast.LENGTH_SHORT).show()
            }
            back_pressed = System.currentTimeMillis()

        }
    }
    private fun revealAnimation(intent: Intent){
        var rootLayout = findViewById<DrawerLayout>(R.id.drawer)
        mRevealAnimation = RevealAnimation(rootLayout, intent, this)
    }

    private fun openLogin(authentication : TextView){
        authentication.setOnClickListener {
            var loginLayoutIntent = Intent(this@MainActivity, Login_layout::class.java)
            startActivity(loginLayoutIntent)
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            );
            finish()

        }
    }
    private  fun openAccountSettings(accountSettings : TextView){
        accountSettings.setOnClickListener {
            var accountSettingsIntent = Intent(this@MainActivity, Settings_layout::class.java)
            startActivity(accountSettingsIntent)
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            finish()
        }
    }

    private fun setupDrawer(user : User){
        toolbar = findViewById(R.id.drawer_toolbar_toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer)
        var header = LayoutInflater.from(this).inflate(R.layout.drawer_header_logged, null)
        var loggedLayout = header.findViewById<View>(R.id.drawer_logged)
        navigationView = findViewById(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener(this@MainActivity)

        var image = user.image
        //Font of the drawer:
        if(!user.email.equals("") && !user.userName.equals(""))
        if(user.getFont().equals("Normal")) {
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            loggedLayout.findViewById<TextView>(R.id.drawer_logged_userName).text = user.userName
            Picasso.get()
                .load(image)
                .transform(CropCircleTransformation())
                .into(loggedLayout.findViewById<ImageView>(R.id.drawer_logged_userImage))
            navigationView.addHeaderView(loggedLayout)
        }
        else if(user.getFont().equals("Normal2")){

        }
        //default
        else{
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            loggedLayout.findViewById<TextView>(R.id.drawer_logged_userName).text = user.userName
            if(!image.equals(""))
            Picasso.get()
                .load(image)
                .transform(CropCircleTransformation())
                .into(loggedLayout.findViewById<ImageView>(R.id.drawer_logged_userImage))
            navigationView.addHeaderView(loggedLayout)
        }

        actionBarToggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.isDrawerSlideAnimationEnabled
        actionBarToggle.syncState()
    }

    fun loadUser() : User {
        var sharedPreferences : SharedPreferences = getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        var userName = sharedPreferences.getString("USERNAME", "")
        var email = sharedPreferences.getString("EMAIL", "")
        var img = sharedPreferences.getString("IMAGE","")
        var user = User(userName, email, img)
        DocumentManager.getInstance().setUser(user)
        return DocumentManager.getInstance().user
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentHome()
                    )
                    .commit()
            }
            R.id.nav_allNotes -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentAllNotes()
                    )
                    .commit()
            }
            R.id.nav_favoriteNotes -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentFavoriteNotes()
                    )
                    .commit()
            }
            R.id.nav_calendar -> {
                //initialize the events in the calendar
                //initializeEvents()
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentCalendar()
                    )
                    .commit()
            }
            R.id.nav_reminder -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentReminder()
                    )
                    .commit()
            }
            R.id.nav_waterReminder -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentWaterReminder()
                    )
                    .commit()
            }
            R.id.nav_trash -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentTrash()
                    )
                    .commit()
            }
            R.id.nav_conversations -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentConversations()
                    )
                    .commit()
            }
            R.id.nav_sharedNotes -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentSharedNotes()
                    )
                    .commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentSettings()
                    )
                    .commit()
            }

            else -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        FragmentHome()
                    )
                    .commit()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    fun showAddElementMenu(view: View) {
        //setting dialog for showing the add element menu
        addElementDialog = Dialog(this)
        addElementDialog.setContentView(R.layout.dialog_add_element_layout)
        addElementDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addElementDialog.show()
    }
    fun showAddEventMenu(view: View) {
        //setting dialog for showing the add element menu
        addEventDialog = Dialog(this)
        addEventDialog.setContentView(R.layout.dialog_add_event_layout)
        addEventDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addEventDialog.show()
    }

    fun addTextNote(view: View){
        var note = TextNote(4,"Welcome","Welcome to EasyNotes, with us you can make your life simplier than ever before!")
        var array = DataConfig.addElement(0, PrefConfigNotesArray.readListFromPref(this), note)
        PrefConfigNotesArray.writeListInPref(this, array)
        var accountSettingsIntent = Intent(this@MainActivity, MainActivity::class.java)
        startActivity(accountSettingsIntent)
    }

    fun addFolder(view: View){
        addElementDialog.dismiss()
    }

    fun addCrossNote(view: View){
        addElementDialog.dismiss()
    }
    fun addAudio(view : View){
        addElementDialog.dismiss()
    }
    fun addImage(view : View){
        addElementDialog.dismiss()
    }

}
