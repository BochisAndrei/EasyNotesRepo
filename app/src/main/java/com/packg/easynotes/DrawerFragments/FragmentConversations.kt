package com.packg.easynotes.DrawerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.packg.easynotes.R

class FragmentConversations : Fragment() {

    companion object {

        fun newInstance(): FragmentConversations {
            return FragmentConversations()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conversations, container,false)
    }


}