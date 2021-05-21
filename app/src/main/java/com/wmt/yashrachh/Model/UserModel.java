package com.wmt.yashrachh.Model;

public class UserModel {

    public String thumbnail;
    public String first;
    public String last;
    public String email;
    public String date;

    public UserModel() {
    }

    public UserModel(String thumbnail, String first, String last, String email, String date) {
        this.thumbnail = thumbnail;
        this.first = first;
        this.last = last;
        this.email = email;
        this.date = date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
