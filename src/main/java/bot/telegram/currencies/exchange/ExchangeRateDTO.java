package bot.telegram.currencies.exchange;

public class ExchangeRateDTO {
    private double USDRateBuy;
    private double USDRateSell;
    private double EURRateBuy;
    private double EURRateSell;

    public double getUSDRateBuy() {
        return USDRateBuy;
    }

    public void setUSDRateBuy(double USDRateBuy) {
        this.USDRateBuy = USDRateBuy;
    }

    public double getUSDRateSell() {
        return USDRateSell;
    }

    public void setUSDRateSell(double USDRateSell) {
        this.USDRateSell = USDRateSell;
    }

    public double getEURRateBuy() {
        return EURRateBuy;
    }

    public void setEURRateBuy(double EURRateBuy) {
        this.EURRateBuy = EURRateBuy;
    }

    public double getEURRateSell() {
        return EURRateSell;
    }

    public void setEURRateSell(double EURRateSell) {
        this.EURRateSell = EURRateSell;
    }

    @Override
    public String toString() {
        return "ExchangeRateDTO{" +
                "USDRateBuy=" + USDRateBuy +
                ", USDRateSell=" + USDRateSell +
                ", EURRateBuy=" + EURRateBuy +
                ", EURRateSell=" + EURRateSell +
                '}';
    }
}
