package com.packg.easynotes.DrawerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.packg.easynotes.R

class FragmentSettings : Fragment() {

    companion object {

        fun newInstance(): FragmentSettings {
            return FragmentSettings()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_settings, container,false)
    }


}