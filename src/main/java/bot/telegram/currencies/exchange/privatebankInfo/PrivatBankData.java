package bot.telegram.currencies.exchange.privatebankInfo;

import com.google.gson.annotations.SerializedName;

class PrivatBankData {
    @SerializedName("ccy")
    private String currencyFrom;
    @SerializedName("base_ccy")
    private String currencyTo;
    @SerializedName("sale")
    private float rateSell;
    @SerializedName("buy")
    private float rateBuy;
    public PrivatBankData(String currencyFrom, String currencyTo, float rateSell, float rateBuy) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rateSell = rateSell;
        this.rateBuy = rateBuy;
    }
    public String getCurrencyFrom() {
        return currencyFrom;
    }
    public String getCurrencyTo() {
        return currencyTo;
    }
    public float getRateSell() {
        return rateSell;
    }
    public float getRateBuy() {
        return rateBuy;
    }
}
