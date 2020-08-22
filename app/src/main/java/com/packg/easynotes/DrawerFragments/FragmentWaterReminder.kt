package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.packg.easynotes.Activitys.ISelectedData
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R


class FragmentWaterReminder : Fragment(), ISelectedData {

    private var sharedP = "SHARED_USER"

    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    private var progr = 0
    lateinit var button_250: Button
    lateinit var button_500: Button
    lateinit var button_1L: Button
    lateinit var progress_bar: ProgressBar
    lateinit var text_view_progress: TextView
    lateinit var quoteList: ArrayList<String>
    lateinit var water_consumed: TextView
    lateinit var currentWaterLevel: String
    lateinit var waterLimit: String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    companion object {

        fun newInstance(): FragmentWaterReminder {
            return FragmentWaterReminder()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_water_reminder, container,false)
        (activity as MainActivity).toolbar.findViewById<TextView>(R.id.main_activity_toolbar_title).text = getString(
                    R.string.WaterReminder)
        (activity as MainActivity).toolbar.findViewById<ImageView>(R.id.drawer_toolbar_search).visibility = View.INVISIBLE
        (activity as MainActivity).toolbar.background = ContextCompat.getDrawable(activity as MainActivity, R.drawable.bg_water_reminder)

        sharedPreferences = this.requireActivity().getSharedPreferences(sharedP, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        button_250 = view.findViewById(R.id.fragment_water_reminder_incr250ml)
        button_500 = view.findViewById(R.id.fragment_water_reminder_incr500ml)
        button_1L = view.findViewById(R.id.fragment_water_reminder_incr1L)
        progress_bar = view.findViewById(R.id.progress_bar)
        text_view_progress = view.findViewById(R.id.text_view_progress)
        water_consumed = view.findViewById(R.id.fragment_water_reminder_water_consumed)

        updateWaterMinMax()

        water_consumed.text = "$currentWaterLevel / $waterLimit"

        quoteList = ArrayList()
        quoteList.add("\"With every drop of water you drink, every breath you take, " +
                "you're connected to the sea. No matter where on earth you live.\"")
        quoteList.add("\"Drinking water is like taking a shower on the inside of your body.\"")
        quoteList.add("\"Thousands have lived without love, not one without water.\"")
        quoteList.add("\"If you drink enough water in the morning, you will feel happier, sharper and more energetic throughout the day.\"")

        val quote = view.findViewById<TextView>(R.id.fragment_water_reminder_quote)
        val buttonChangeLimit = view.findViewById<Button>(R.id.fragment_water_reminder_change_limit)

        quote.text = quoteList.random()
        val face = Typeface.createFromAsset((activity as MainActivity).assets, "fonts/great_vibes_regular.ttf")
        quote.typeface = face

        updateProgressBar()
        button_250.setOnClickListener {
            currentWaterLevel = (currentWaterLevel.toInt() + 250).toString()
            editor.putString("CURRENT_WATER", currentWaterLevel)
            editor.apply()
            updateProgressBar()
        }
        button_500.setOnClickListener {
            currentWaterLevel = (currentWaterLevel.toInt() + 500).toString()
            editor.putString("CURRENT_WATER", currentWaterLevel)
            editor.apply()
            updateProgressBar()

        }
        button_1L.setOnClickListener {
            currentWaterLevel = (currentWaterLevel.toInt() + 1000).toString()
            editor.putString("CURRENT_WATER", currentWaterLevel)
            editor.apply()
            updateProgressBar()

        }

        buttonChangeLimit.setOnClickListener {
            var dialog = DialogFragmentChangeWaterLimit()
            dialog.addListener(this)
            dialog.show(requireActivity().supportFragmentManager, "Change limit dialog")
        }

        return view
    }

    private fun updateWaterMinMax() {
        currentWaterLevel = sharedPreferences.getString("CURRENT_WATER", "")

        if(currentWaterLevel == ""){
            editor.putString("CURRENT_WATER", "0")
            currentWaterLevel = "0"
            editor.apply()
        }
        waterLimit = sharedPreferences.getString("WATER_LIMIT", "")

        if(waterLimit == ""){
            editor.putString("WATER_LIMIT","2000")
            waterLimit = "2000"
            editor.apply()
        }
    }

    private fun updateProgressBar() {
        if(currentWaterLevel.toInt() == 0){
            progr = 0
        }else{
            progr = ((currentWaterLevel.toFloat() / waterLimit.toFloat()) * 100).toInt()
        }
        progress_bar.progress = progr
        text_view_progress.text = "$progr%"
        water_consumed.text = "$currentWaterLevel / $waterLimit"
    }

    override fun onSelectedData(string: String?) {
        this.water_consumed.text = (currentWaterLevel + "/" + string?.substring(0, string.length - 3))
        waterLimit = string?.substring(0, string.length - 3).toString()
        editor.putString("WATER_LIMIT", string?.substring(0, string.length - 3))
        editor.apply()
    }


}