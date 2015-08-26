package com.ysmal.ssc.model;

import java.util.Date;

public class UserInfo {
    private String name;
    private String filter;
    private Date timeLogOn;

    public UserInfo() {
        this.filter = "";
    }

    public String getName() {
        return name;
    }

    public String getFilter() {
        return filter;
    }

    public Date getTimeLogOn() {
        return timeLogOn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setTimeLogOn(Date timeLogOn) {
        this.timeLogOn = timeLogOn;
    }
}
