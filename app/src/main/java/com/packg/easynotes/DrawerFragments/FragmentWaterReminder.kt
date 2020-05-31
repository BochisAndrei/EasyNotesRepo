package com.packg.easynotes.DrawerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.packg.easynotes.R

class FragmentWaterReminder : Fragment() {

    companion object {

        fun newInstance(): FragmentWaterReminder {
            return FragmentWaterReminder()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_water_reminder, container,false)
    }


}