package com.earnbygame.ebgsoldier.model.login;

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
    private int dob;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("no_of_referrals")
    @Expose
    private int noOfReferrals;
    @SerializedName("total_added_amount")
    @Expose
    private int totalAddedAmount;
    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;
    @SerializedName("no_of_match_played")
    @Expose
    private int noOfMatchPlayed;
    @SerializedName("total_earned_match")
    @Expose
    private int totalEarnedMatch;
    @SerializedName("total_earned_refferals")
    @Expose
    private int totalEarnedRefferals;
    @SerializedName("total_kills")
    @Expose
    private int totalKills;
    @SerializedName("rank")
    @Expose
    private int rank;
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

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public int getNoOfReferrals() {
        return noOfReferrals;
    }

    public void setNoOfReferrals(int noOfReferrals) {
        this.noOfReferrals = noOfReferrals;
    }

    public int getTotalAddedAmount() {
        return totalAddedAmount;
    }

    public void setTotalAddedAmount(int totalAddedAmount) {
        this.totalAddedAmount = totalAddedAmount;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public int getNoOfMatchPlayed() {
        return noOfMatchPlayed;
    }

    public void setNoOfMatchPlayed(int noOfMatchPlayed) {
        this.noOfMatchPlayed = noOfMatchPlayed;
    }

    public int getTotalEarnedMatch() {
        return totalEarnedMatch;
    }

    public void setTotalEarnedMatch(int totalEarnedMatch) {
        this.totalEarnedMatch = totalEarnedMatch;
    }

    public int getTotalEarnedRefferals() {
        return totalEarnedRefferals;
    }

    public void setTotalEarnedRefferals(int totalEarnedRefferals) {
        this.totalEarnedRefferals = totalEarnedRefferals;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
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