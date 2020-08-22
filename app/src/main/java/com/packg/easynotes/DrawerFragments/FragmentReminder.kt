package com.packg.easynotes.DrawerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.packg.easynotes.MainActivity.MainActivity
import com.packg.easynotes.R

class FragmentReminder : Fragment() {

    companion object {

        fun newInstance(): FragmentReminder {
            return FragmentReminder()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as MainActivity).toolbar.findViewById<ImageView>(R.id.drawer_toolbar_search).visibility = View.INVISIBLE

        return inflater.inflate(R.layout.fragment_reminder, container,false)
    }


}