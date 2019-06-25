package com.earnbygame.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMatchUserJoined {
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("pubg_user_name")
    @Expose
    private String pubgUserName;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
