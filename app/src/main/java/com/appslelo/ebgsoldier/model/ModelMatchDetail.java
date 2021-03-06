package com.appslelo.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMatchDetail {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("match_name")
    @Expose
    private String matchName;
    @SerializedName("match_date")
    @Expose
    private String matchDate;
    @SerializedName("match_time")
    @Expose
    private String matchTime;
    @SerializedName("prize_label")
    @Expose
    private String prizeLabel;
    @SerializedName("kill_label")
    @Expose
    private String killLabel;
    @SerializedName("fee_label")
    @Expose
    private String feeLabel;
    @SerializedName("first_prize")
    @Expose
    private String firstPrize;
    @SerializedName("per_kill_prize")
    @Expose
    private String perKillPrize;
    @SerializedName("entry_fee")
    @Expose
    private String entryFee;
    @SerializedName("match_type")
    @Expose
    private String matchType;
    @SerializedName("match_version")
    @Expose
    private String matchVersion;
    @SerializedName("match_map")
    @Expose
    private String matchMap;
    @SerializedName("total_players")
    @Expose
    private String totalPlayers;
    @SerializedName("total_joined")
    @Expose
    private String totalJoined;
    @SerializedName("remain_to_join")
    @Expose
    private String remainToJoin;
    @SerializedName("join_date")
    @Expose
    private String joinDate;
    @SerializedName("join_time")
    @Expose
    private String joinTime;
    @SerializedName("winner")
    @Expose
    private Object winner;
    @SerializedName("winning_amount")
    @Expose
    private Object winningAmount;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("room_password")
    @Expose
    private String roomPassword;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("match_image")
    @Expose
    private String matchImage;
    @SerializedName("match_icon")
    @Expose
    private String matchIcon;
    @SerializedName("content")
    @Expose
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getPrizeLabel() {
        return prizeLabel;
    }

    public void setPrizeLabel(String prizeLabel) {
        this.prizeLabel = prizeLabel;
    }

    public String getKillLabel() {
        return killLabel;
    }

    public void setKillLabel(String killLabel) {
        this.killLabel = killLabel;
    }

    public String getFeeLabel() {
        return feeLabel;
    }

    public void setFeeLabel(String feeLabel) {
        this.feeLabel = feeLabel;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        this.firstPrize = firstPrize;
    }

    public String getPerKillPrize() {
        return perKillPrize;
    }

    public void setPerKillPrize(String perKillPrize) {
        this.perKillPrize = perKillPrize;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchVersion() {
        return matchVersion;
    }

    public void setMatchVersion(String matchVersion) {
        this.matchVersion = matchVersion;
    }

    public String getMatchMap() {
        return matchMap;
    }

    public void setMatchMap(String matchMap) {
        this.matchMap = matchMap;
    }

    public String getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(String totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public String getTotalJoined() {
        return totalJoined;
    }

    public void setTotalJoined(String totalJoined) {
        this.totalJoined = totalJoined;
    }

    public String getRemainToJoin() {
        return remainToJoin;
    }

    public void setRemainToJoin(String remainToJoin) {
        this.remainToJoin = remainToJoin;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public Object getWinner() {
        return winner;
    }

    public void setWinner(Object winner) {
        this.winner = winner;
    }

    public Object getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Object winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatchImage() {
        return matchImage;
    }

    public void setMatchImage(String matchImage) {
        this.matchImage = matchImage;
    }

    public String getMatchIcon() {
        return matchIcon;
    }

    public void setMatchIcon(String matchIcon) {
        this.matchIcon = matchIcon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
