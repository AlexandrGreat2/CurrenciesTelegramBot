package bot.telegram.currencies.exchange.monobankInfo;

import com.google.gson.annotations.SerializedName;

class MonoBankData {
    @SerializedName("currencyCodeA")
    private int currencyFrom;
    @SerializedName("currencyCodeB")
    private int currencyTo;
    private float rateSell;
    private float rateBuy;
    public MonoBankData(int currencyFrom, int currencyTo, float rateSell, float rateBuy) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rateSell = rateSell;
        this.rateBuy = rateBuy;
    }
    public int getCurrencyFrom() {
        return currencyFrom;
    }
    public int getCurrencyTo() {
        return currencyTo;
    }
    public float getRateSell() {
        return rateSell;
    }
    public float getRateBuy() {
        return rateBuy;
    }
}
