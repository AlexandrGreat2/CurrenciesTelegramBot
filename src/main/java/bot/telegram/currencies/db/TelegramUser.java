package bot.telegram.currencies.db;

import java.util.List;

public class TelegramUser {
    private long userId;
    private Config config;

    public TelegramUser(long userId, Config config) {
        this.userId = userId;
        this.config = config;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getDecimalPlaces() {
        return String.valueOf(config.getDecimalPlaces());
    }

    public void setDecimalPlaces(String decimalPlaces) {
        config.setDecimalPlaces(Integer.parseInt(decimalPlaces));
    }

    public String getBank() {
        return config.getBank();
    }

    public void setBank(String bank) {
        config.setBank(bank);
    }

    public List<String> getCurrencies() {
        return config.getCurrencies();
    }

    public void setCurrencies(List<String> currencies) {
        config.setCurrencies(currencies);
    }

    public String getNotificationTime() {
        return config.getNotificationTime();
    }

    public void setNotificationTime(String notificationTime) {
        config.setNotificationTime(notificationTime);
    }

    public String getLanguage() {
        return config.getLanguage();
    }

    public void setLanguage(String language) {
        config.setLanguage(language);
    }

    @Override
    public String toString() {
        return "TelegramUser{" +
                "userId=" + userId +
                ", config=" + config +
                '}';
    }
}
