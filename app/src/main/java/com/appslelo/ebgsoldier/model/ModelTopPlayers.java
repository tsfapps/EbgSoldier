package com.appslelo.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTopPlayers {
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("pubg_user_name")
    @Expose
    private String pubgUserName;
    @SerializedName("total_earned_match")
    @Expose
    private String totalEarnedMatch;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPubgUserName() {
        return pubgUserName;
    }

    public void setPubgUserName(String pubgUserName) {
        this.pubgUserName = pubgUserName;
    }

    public String getTotalEarnedMatch() {
        return totalEarnedMatch;
    }

    public void setTotalEarnedMatch(String totalEarnedMatch) {
        this.totalEarnedMatch = totalEarnedMatch;
    }

}
