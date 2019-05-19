package com.earnbygame.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class User extends SugarRecord {

    @SerializedName("id")
    @Expose
    private transient String t_id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("pubg_user_name")
    @Expose
    private String pubgUserName;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("no_of_referrals")
    @Expose
    private Object noOfReferrals;
    @SerializedName("total_added_amount")
    @Expose
    private Object totalAddedAmount;
    @SerializedName("wallet_amount")
    @Expose
    private Object walletAmount;
    @SerializedName("no_of_match_played")
    @Expose
    private Object noOfMatchPlayed;
    @SerializedName("total_earned_match")
    @Expose
    private Object totalEarnedMatch;
    @SerializedName("total_earned_refferals")
    @Expose
    private Object totalEarnedRefferals;
    @SerializedName("total_kills")
    @Expose
    private Object totalKills;
    @SerializedName("rank")
    @Expose
    private Object rank;
    @SerializedName("pubg_level")
    @Expose
    private String pubgLevel;
    @SerializedName("doj")
    @Expose
    private String doj;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("status")
    @Expose
    private String status;

    public String getTId() {
        return t_id;
    }

    public void setTId(String id) {
        this.t_id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Object getNoOfReferrals() {
        return noOfReferrals;
    }

    public void setNoOfReferrals(Object noOfReferrals) {
        this.noOfReferrals = noOfReferrals;
    }

    public Object getTotalAddedAmount() {
        return totalAddedAmount;
    }

    public void setTotalAddedAmount(Object totalAddedAmount) {
        this.totalAddedAmount = totalAddedAmount;
    }

    public Object getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Object walletAmount) {
        this.walletAmount = walletAmount;
    }

    public Object getNoOfMatchPlayed() {
        return noOfMatchPlayed;
    }

    public void setNoOfMatchPlayed(Object noOfMatchPlayed) {
        this.noOfMatchPlayed = noOfMatchPlayed;
    }

    public Object getTotalEarnedMatch() {
        return totalEarnedMatch;
    }

    public void setTotalEarnedMatch(Object totalEarnedMatch) {
        this.totalEarnedMatch = totalEarnedMatch;
    }

    public Object getTotalEarnedRefferals() {
        return totalEarnedRefferals;
    }

    public void setTotalEarnedRefferals(Object totalEarnedRefferals) {
        this.totalEarnedRefferals = totalEarnedRefferals;
    }

    public Object getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(Object totalKills) {
        this.totalKills = totalKills;
    }

    public Object getRank() {
        return rank;
    }

    public void setRank(Object rank) {
        this.rank = rank;
    }

    public String getPubgLevel() {
        return pubgLevel;
    }

    public void setPubgLevel(String pubgLevel) {
        this.pubgLevel = pubgLevel;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}