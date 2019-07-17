package com.appslelo.ebgsoldier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelWalletBonus {
    @SerializedName("total_earned_refferals")
    @Expose
    private String totalEarnedRefferals;
    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;

    public String getTotalEarnedRefferals() {
        return totalEarnedRefferals;
    }

    public void setTotalEarnedRefferals(String totalEarnedRefferals) {
        this.totalEarnedRefferals = totalEarnedRefferals;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }
}