package com.packg.easynotes.Elements;

import java.util.Date;

public class Event {
    private Date date;
    private String eventText;

    public Event(Date date, String eventText){
        this.date = date;
        this.eventText = eventText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }
}
