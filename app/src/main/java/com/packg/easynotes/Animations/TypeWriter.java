package com.packg.easynotes.Animations;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;


public class TypeWriter extends TextView {

    private CharSequence text;
    private int index;
    private long delay = 150;

    public TypeWriter(Context context) {
        super(context);
    }
    public TypeWriter(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    private Handler handler = new Handler();

    private Runnable characterAdd = new Runnable(){

        @Override
        public void run() {
            setText(text.subSequence(0, index++));

            if (index <= text.length()){
                handler.postDelayed(characterAdd, delay);
            }
        }
    };

    public void animateText(CharSequence txt){
        text = txt;
        index = 0;

        setText("");
        handler.removeCallbacks(characterAdd);
        handler.postDelayed(characterAdd, delay);
    }

    public void setCharacterDelay(long m){
        delay = m;
    }
}
