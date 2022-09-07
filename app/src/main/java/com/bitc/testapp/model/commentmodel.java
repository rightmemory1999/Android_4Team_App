package com.bitc.testapp.model;

public class commentmodel {

    String date,time,uid,userimage,usermsg,username;

    public commentmodel(){

    }




    public commentmodel(String date, String time, String uid, String userimage, String usermsg, String username) {
        this.date = date;
        this.time = time;
        this.uid = uid;
        this.userimage = userimage;
        this.usermsg = usermsg;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
