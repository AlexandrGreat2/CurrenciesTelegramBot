package bot.telegram.currencies.message;

import bot.telegram.currencies.db.BankExchangeRates;
import bot.telegram.currencies.db.TelegramUser;

import java.util.List;

public interface MessageBuilder {
    public String getGreetings();

    public String getInformation();

    public String getSettings();

    public String getSettingsDecimalPlaceTitle();

    public String getSettingsBankTitle();

    public String getSettingsCurrenciesTitle();
    public String getSettingsNotificationTimeTitle();

    public String getChoose();

    public List<String> getDecimalPlaceSettings();

    public List<String> getBankSettings();

    public List<String> getCurrenciesSettings();

    public List<String> getNotificationTimeSettings();
    public String getActualInformation(TelegramUser user, BankExchangeRates bankExchangeRates);
    public String getLanguageTitle();
    public List<String> getLanguageSettings();
    public List<String> setLanguageSettings(TelegramUser user);
    public List<String> setDecimalPlaceSettings(TelegramUser user);

    public List<String> setBankSettings(TelegramUser user);

    public List<String> setCurrenciesSettings(TelegramUser user);
    public String showUserSettings(TelegramUser user);
}
