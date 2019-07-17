package com.appslelo.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNotification {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("not_heading")
    @Expose
    private String notHeading;
    @SerializedName("not_content")
    @Expose
    private String notContent;
    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotHeading() {
        return notHeading;
    }

    public void setNotHeading(String notHeading) {
        this.notHeading = notHeading;
    }

    public String getNotContent() {
        return notContent;
    }

    public void setNotContent(String notContent) {
        this.notContent = notContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
