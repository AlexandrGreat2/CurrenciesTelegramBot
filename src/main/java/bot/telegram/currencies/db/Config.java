package bot.telegram.currencies.db;

import java.util.List;

public class Config {
    private int decimalPlaces;
    private String bank;
    private List<String> currencies;
    private String notificationTime;
    private String language;

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Config{" +
                "decimalPlaces=" + decimalPlaces +
                ", bank='" + bank + '\'' +
                ", currencies=" + currencies +
                ", notificationTime='" + notificationTime + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
