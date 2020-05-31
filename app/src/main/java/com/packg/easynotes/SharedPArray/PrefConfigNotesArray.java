package com.packg.easynotes.SharedPArray;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.packg.easynotes.Singleton.DocumentManager;
import com.packg.easynotes.Elements.Audio;
import com.packg.easynotes.Elements.CrossNote;
import com.packg.easynotes.Elements.Element;
import com.packg.easynotes.Elements.Event;
import com.packg.easynotes.Elements.Folder;
import com.packg.easynotes.Elements.TextNote;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefConfigNotesArray {

    private static final String LIST_KEY = "NOTES_ARRAY";
    private static final String EVENT = "EVENT";

    public static void writeListInPref(Context context, ArrayList<Element> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LIST_KEY);
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static ArrayList<Element> readListFromPref(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sharedPreferences.getString(LIST_KEY,"");
        // adding all different container classes with their flag
        final RuntimeTypeAdapterFactory<Element> typeFactory = RuntimeTypeAdapterFactory
                .of(Element.class, "type")
                // Here you specify which is the parent class and what field particularizes the child class.
                .registerSubtype(TextNote.class, "TextNote")
                // if the flag equals the class name, you can skip the second parameter. This is only necessary, when the "type" field does not equal the class name.
                .registerSubtype(Folder.class, "Folder")
                .registerSubtype(CrossNote.class, "CrossNote")
                .registerSubtype(Audio.class, "Audio");

        Gson gson =  new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
        Type type = new TypeToken<ArrayList<Element>>(){}.getType();
        ArrayList<Element> list = gson.fromJson(jsonString, type);
        return list;
    }
    public static ArrayList<Event> readEventListFromPref(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sharedPreferences.getString(EVENT, "");
        Gson gson = new Gson();
        ArrayList<Event> list = gson.fromJson(jsonString, new TypeToken<ArrayList<Event>>(){}.getType());
        return list;
    }
    public static void writeEventListInPref(Context context, ArrayList<Event> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EVENT, jsonString);
        editor.apply();
    }
    public static boolean arrayEventIsEmpty(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sharedPreferences.getString(EVENT,"");
        if(jsonString.isEmpty()) return true;
        else return false;
    }

    public static boolean arrayIsEmpty(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sharedPreferences.getString(LIST_KEY,"");
        if(jsonString.isEmpty()) return true;
        else return false;
    }

    public static Element findElement(Element e){
        ArrayList<Element> arrayList = DocumentManager.getInstance().getList();

        return e;
    }
    public static void addElement(Element e){

    }
}
