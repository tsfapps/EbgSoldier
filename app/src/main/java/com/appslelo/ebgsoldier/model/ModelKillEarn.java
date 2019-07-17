package com.appslelo.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelKillEarn {
    @SerializedName("no_of_match_played")
    @Expose
    private String noOfMatchPlayed;
    @SerializedName("total_earned_match")
    @Expose
    private String totalEarnedMatch;
    @SerializedName("total_kills")
    @Expose
    private String totalKills;

    public String getNoOfMatchPlayed() {
        return noOfMatchPlayed;
    }

    public void setNoOfMatchPlayed(String noOfMatchPlayed) {
        this.noOfMatchPlayed = noOfMatchPlayed;
    }

    public String getTotalEarnedMatch() {
        return totalEarnedMatch;
    }

    public void setTotalEarnedMatch(String totalEarnedMatch) {
        this.totalEarnedMatch = totalEarnedMatch;
    }

    public String getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(String totalKills) {
        this.totalKills = totalKills;
    }

}
