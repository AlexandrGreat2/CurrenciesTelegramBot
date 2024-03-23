package bot.telegram.currencies.exchange.nbuInfo;

import com.google.gson.annotations.SerializedName;

class NBUData{
    @SerializedName("r030")
    private int currency;
    private float rate;
    public NBUData(int currency, float rate) {
        this.currency = currency;
        this.rate = rate;
    }
    public int getCurrency() {
        return currency;
    }
    public float getRate() {
        return rate;
    }
}