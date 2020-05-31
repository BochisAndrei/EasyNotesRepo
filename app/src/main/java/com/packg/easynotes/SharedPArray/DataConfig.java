package com.packg.easynotes.SharedPArray;

import com.packg.easynotes.Elements.Element;
import com.packg.easynotes.Elements.Folder;

import java.util.ArrayList;

public class DataConfig {

    public static ArrayList<Element> addElement(int folderId, ArrayList<Element> list, Element el){
        list.add(el);


        return list;
    }
    public static ArrayList<Element> removeElement(ArrayList<Element> array, Element el){
        for(Element e : array){
            if(e.getId() == 0){
                array.add(el);
            }
            if(e instanceof Folder){
                Folder f = (Folder) e;
                if(e.getId() == el.getId()){
                    f.getElementsList().remove(el);
                }else{
                    removeElement(f.getElementsList(), el);
                }
            }
        }
        return array;
    }
}
