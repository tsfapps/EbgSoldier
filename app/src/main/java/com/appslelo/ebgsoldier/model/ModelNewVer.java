package com.appslelo.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNewVer {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ver_name")
    @Expose
    private String verName;
    @SerializedName("update_msg")
    @Expose
    private String updateMsg;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

}
