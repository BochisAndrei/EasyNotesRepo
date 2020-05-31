package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Singleton.DocumentManager
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R


class FragmentHome : Fragment() {

    companion object {

        fun newInstance(): FragmentHome {
            return FragmentHome()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container,false)

        var myFolderList = ArrayList<Element>()
        var array = DocumentManager.getInstance().list

        if(array!=null){
            myFolderList = array
        }

        val activity = activity as Context

        val recyclerView = view.findViewById<RecyclerView>(R.id.home_recycler_view)
        var rvHomeAdapter = RVAdapterHome(activity, myFolderList)
        recyclerView.layoutManager = GridLayoutManager(activity, Utility.calculateNoOfColumns(activity, 200f))
        recyclerView.adapter = rvHomeAdapter
        return view
    }

    object Utility {
        fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {
            val displayMetrics = context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            return (screenWidthDp / columnWidthDp + 0.5).toInt()
        }
    }


}