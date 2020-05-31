package com.packg.easynotes.DrawerFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.packg.easynotes.Singleton.DocumentManager
import com.packg.easynotes.Elements.*
import com.packg.easynotes.R


class RVAdapterHome(var context: Context, var arrayList: ArrayList<Element>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if(arrayList[position] is Folder)
            return 1 // 1 is Folder Type
        else if(arrayList[position] is CrossNote)
            return 2 // 2 is Crossnote Type
        else if (arrayList[position] is TextNote)
            return 3 // 3 is TextNote Type
        else if(arrayList[position] is Audio)
            return 4 // 4 is Audio Type
        else if(arrayList[position] is Image)
            return 5 // 5 is Image Type
        else if(arrayList[position] is ProxyImage)
            return 6 // 6 is ProxyImage Type
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view : View

        when (viewType) {
            2 -> {// 2 is Crossnote Type
                view = LayoutInflater.from(context).inflate(R.layout.card_view_cross_note, parent, false)
                return CrossNoteViewHolder(view)
            }
            3 -> {// 3 is TextNote Type
                view = LayoutInflater.from(context).inflate(R.layout.card_view_text_note, parent, false)
                return TextNoteViewHolder(view)
            }
            4->{// 4 is Audio Type
                view = LayoutInflater.from(context).inflate(R.layout.card_view_folder, parent, false)
                return AudioViewHolder(view)
            }
            5->{ // 5 is Image Type
                view = LayoutInflater.from(context).inflate(R.layout.card_view_folder, parent, false)
                return ImageViewHolder(view)
            }
            6->{// 6 is ProxyImage Type
                view = LayoutInflater.from(context).inflate(R.layout.card_view_folder, parent, false)
                return FolderViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(context).inflate(R.layout.card_view_folder, parent, false)
                return FolderViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var type : Element
        when (holder.getItemViewType()){
            1 -> {// 1 is Folder Type
                type = arrayList[position] as Folder
                (holder as FolderViewHolder)
                    .initializeUIComponents(type.name, type.image)
                holder.cardView.setOnClickListener {
                    arrayList.clear()
                    var myFolderList = (type as Folder).getElementsList()
                    DocumentManager.getInstance().currentFolderId = type.id
                    arrayList.addAll(myFolderList)
                    this.notifyDataSetChanged()
                }
            }
            2 -> {// 2 is Crossnote Type
                type = arrayList[position] as CrossNote
                (holder as CrossNoteViewHolder)
                    .initializeUIComponents(type.name)

            }
            3 -> {// 3 is TextNote Type
                type = arrayList[position] as TextNote
                (holder as TextNoteViewHolder)
                    .initializeUIComponents(type.name, type.text)

            }
            4 -> {// 4 is Audio Type
                type = arrayList[position] as Audio
                (holder as AudioViewHolder)
                    .initializeUIComponents(type.name)

            }
            5 -> {// 5 is Image Type
                type = arrayList[position] as Image
                (holder as ImageViewHolder)
                    .initializeUIComponents(type.name)
            }
        }
    }


    //class for Folder card_view
    inner class FolderViewHolder(myView: View) : RecyclerView.ViewHolder(myView){
        var folder_title = myView.findViewById<TextView>(R.id.card_view_folder_title)
        var folder_image = myView.findViewById<ImageView>(R.id.card_view_folder_image)
        lateinit var cardView : CardView

        fun initializeUIComponents(fName : String, fImage: Int){
            var name = fName
            if(fName.length > 12)
                name = fName.substring(0,12) + "..."
            folder_title.text = name
            folder_image.setImageResource(fImage)
            cardView = itemView.findViewById(R.id.card_view_folder_id)
        }
    }

    // class for CrossNote card_view
    inner class CrossNoteViewHolder(myView: View) : RecyclerView.ViewHolder(myView){

        var crossnote_title = myView.findViewById<TextView>(R.id.card_view_cross_note_title)

        fun initializeUIComponents(cName : String){
            var name = cName
            if(cName.length > 12)
                name = cName.substring(0,12) + "..."
            crossnote_title.text = name
        }
    }

    //class for Text Note card_view
    inner class TextNoteViewHolder(myView: View) : RecyclerView.ViewHolder(myView){

        var text_note_title = myView.findViewById<TextView>(R.id.card_view_text_note_title)
        var text_note_content = myView.findViewById<TextView>(R.id.card_view_text_note_content)

        fun initializeUIComponents(fName : String, text : String){
            var name = fName
            if(fName.length > 12)
                name = fName.substring(0,12) + "..."
            text_note_title.text = name
            text_note_content.text = text

        }
    }

    //class for Audio Note card_view
    inner class AudioViewHolder(myView: View) : RecyclerView.ViewHolder(myView){

        var folder_title = myView.findViewById<TextView>(R.id.card_view_folder_title)
        var folder_image = myView.findViewById<ImageView>(R.id.card_view_folder_image)

        fun initializeUIComponents(fName : String){
            var name = fName
            if(fName.length > 12)
                name = fName.substring(0,12) + "..."
            folder_title.text = name
            folder_image.setImageResource(R.drawable.bg_good_evening)
        }
    }

    //class for Image Note card_view
    inner class ImageViewHolder(myView: View) : RecyclerView.ViewHolder(myView){

        var folder_title = myView.findViewById<TextView>(R.id.card_view_folder_title)
        var folder_image = myView.findViewById<ImageView>(R.id.card_view_folder_image)

        fun initializeUIComponents(fName : String){
            var name = fName
            if(fName.length > 12)
                name = fName.substring(0,12) + "..."
            folder_title.text = name
            //folder_image.setImageResource(fImage) //TODO VA FI NEVOIE DE UN URL CU IMAGINEA, PROBABIL DIN BAZA DE DATE
        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }


}