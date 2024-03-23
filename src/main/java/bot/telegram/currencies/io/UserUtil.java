package bot.telegram.currencies.io;

import java.util.List;

public class UserUtil {

    private String decimalPlaces;
    private String bank;
    private List<String> currencies;
    private String notificationTime;

    public UserUtil(String decimalPlaces, String bank, List<String> currencies, String notificationTime) {
        this.decimalPlaces = decimalPlaces;
        this.bank = bank;
        this.currencies = currencies;
        this.notificationTime = notificationTime;
    }

    public String getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(String decimalPlaces) {
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

    @Override
    public String toString() {
        return "UserUtil{" +
                "decimalPlaces='" + decimalPlaces + '\'' +
                ", bank='" + bank + '\'' +
                ", currencies=" + currencies +
                ", notificationTime='" + notificationTime + '\'' +
                '}';
    }
}
