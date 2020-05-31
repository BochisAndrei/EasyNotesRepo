package com.packg.easynotes.Elements;

public class CheckBox {
    protected boolean checked;
    protected String text;
    protected String type;

    public CheckBox(boolean checked, String text){
        this.checked = checked;
        this.text = text;
        this.type = "CheckBox";
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
