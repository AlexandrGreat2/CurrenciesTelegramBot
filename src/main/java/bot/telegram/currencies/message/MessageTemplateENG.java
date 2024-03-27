package bot.telegram.currencies.message;

import bot.telegram.currencies.db.BankExchangeRates;
import bot.telegram.currencies.db.TelegramUser;
import bot.telegram.currencies.exchange.ExchangeRateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MessageTemplateENG implements MessageTemplate {

    public final String GREETINGS = new String("Hello! This bot will help you track current exchange rates!\nChoose one of the following actions:".getBytes(), StandardCharsets.UTF_8);
    public final String GET_INFORMATION = new String("Get information".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS = new String("Settings".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_DECIMAL_PLACE_TITLE = new String("Number of decimal places".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_BANK_TITLE = new String("Bank".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_CURRENCIES_TITLE = new String("Currencies".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_NOTIFICATION_TIME_TITLE = new String("Notification time".getBytes(), StandardCharsets.UTF_8);
    public final String CHOOSE = new String("Choose ".getBytes(), StandardCharsets.UTF_8);
    public final List<String> DECIMAL_PLACE_SETTINGS = List.of("2", "3", "4");
    public final String SETTINGS_LANGUAGE_TITLE = new String("Language".getBytes(), StandardCharsets.UTF_8);
    public final List<String> BANK_SETTINGS = List.of(
            new String("bank1".getBytes(), StandardCharsets.UTF_8),
            new String("bank2".getBytes(), StandardCharsets.UTF_8),
            new String("bank3".getBytes(), StandardCharsets.UTF_8)
    );
    public final List<String> CURRENCIES_SETTINGS = List.of("USD", "EUR");
    public final List<String> NOTIFICATION_TIME_SETTINGS = List.of(
            "9:00", "10:00", "11:00",
            "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00",
            "18:00", new String("Switch off notifications".getBytes(), StandardCharsets.UTF_8)
    );

    public final List<String> LANGUAGE_SETTINGS = List.of(
            new String("Ukrainian".getBytes(), StandardCharsets.UTF_8),
            new String("English".getBytes(), StandardCharsets.UTF_8));

    public String getGreetings() {
        return GREETINGS;
    }

    public String getInformation() {
        return GET_INFORMATION;
    }

    public String getSettings() {
        return SETTINGS;
    }

    public String getSettingsDecimalPlaceTitle() {
        return SETTINGS_DECIMAL_PLACE_TITLE;
    }

    public String getSettingsBankTitle() {
        return SETTINGS_BANK_TITLE;
    }

    public String getSettingsCurrenciesTitle() {
        return SETTINGS_CURRENCIES_TITLE;
    }

    public String getSettingsNotificationTimeTitle() {
        return SETTINGS_NOTIFICATION_TIME_TITLE;
    }

    public String getChoose() {
        return CHOOSE;
    }

    public List<String> getDecimalPlaceSettings() {
        return DECIMAL_PLACE_SETTINGS;
    }

    public List<String> getBankSettings() {
        return BANK_SETTINGS;
    }

    public List<String> getCurrenciesSettings() {
        return CURRENCIES_SETTINGS;
    }

    public List<String> getNotificationTimeSettings() {
        return NOTIFICATION_TIME_SETTINGS;
    }
    @Override
    public String getLanguageTitle() {
        return SETTINGS_LANGUAGE_TITLE;
    }
    @Override
    public List<String> getLanguageSettings() {
        return LANGUAGE_SETTINGS;
    }
    @Override
    public List<String> setLanguageSettings(TelegramUser user) {
        List<String> result = new ArrayList<>();
        String userLanguage = user.getLanguage();
        for (String language : LANGUAGE_SETTINGS) {
            if (language.equals(userLanguage)) {
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + language);
            } else {
                result.add(language);
            }
        }
        return result;
    }

    @Override
    public String getActualInformation(TelegramUser user, BankExchangeRates bankExchangeRates) {
        String userBank = user.getBank();
        String decimalPlaces = user.getDecimalPlaces();
        List<String> userCurrencies = user.getCurrencies();
        ExchangeRateDTO bankDTO = getBankDTO(bankExchangeRates, userBank);

        StringBuilder result = new StringBuilder();
        result.append(new String("Exchange Rate of ".getBytes(), StandardCharsets.UTF_8) + translateBank(userBank) + "\n");
        if (userCurrencies.contains("USD")) {
            result.append(new String("USD purchase: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getUSDRateBuy(), decimalPlaces) + "\n");
            result.append(new String("USD sale: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getUSDRateSell(), decimalPlaces) + "\n");
        }
        if (userCurrencies.contains("EUR")) {
            result.append(new String("EUR purchase: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getEURRateBuy(), decimalPlaces) + "\n");
            result.append(new String("EUR sale: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getEURRateSell(), decimalPlaces) + "\n");
        }
        return result.toString();
    }

    public List<String> setDecimalPlaceSettings(TelegramUser user) {
        List<String> result = new ArrayList<>();
        String userDecimalPlace = user.getDecimalPlaces();
        for (String decimalPlace : DECIMAL_PLACE_SETTINGS) {
            if (decimalPlace.equals(userDecimalPlace)) {
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + decimalPlace);
            } else {
                result.add(decimalPlace);
            }
        }
        return result;
    }

    public List<String> setBankSettings(TelegramUser user) {
        List<String> result = new ArrayList<>();
        String userBank = user.getBank();
        for (String bank : BANK_SETTINGS) {
            String bankENG = translateBank(bank);
            if (bank.equals(userBank)) {
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + bankENG);
            } else {
                result.add(bankENG);
            }
        }
        return result;
    }

    private String translateBank(String bank) {
        String result = "";
        switch (bank) {
            case "bank1" : result = new String("NBU".getBytes(), StandardCharsets.UTF_8);
                break;
            case "bank2" : result = new String("MonoBank ".getBytes(), StandardCharsets.UTF_8);
                break;
            case "bank3" : result = new String("PrivatBank".getBytes(), StandardCharsets.UTF_8);
                break;
        }
        return result;
    }

    public List<String> setCurrenciesSettings(TelegramUser user) {
        List<String> result = new ArrayList<>();
        List<String> userCurrencies = user.getCurrencies();
        for (String currency : CURRENCIES_SETTINGS) {
            if (userCurrencies.contains(currency)) {
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + currency);
            } else {
                result.add(currency);
            }
        }
        return result;
    }

    public String showUserSettings(TelegramUser user) {
        return new String("User ID: ".getBytes(), StandardCharsets.UTF_8) + user.getUserId() + "\n" +
                SETTINGS_BANK_TITLE + ": " + user.getBank() + "\n" +
                SETTINGS_CURRENCIES_TITLE + ": " + user.getCurrencies() + "\n" +
                SETTINGS_DECIMAL_PLACE_TITLE + ": " + user.getDecimalPlaces() + "\n" +
                SETTINGS_NOTIFICATION_TIME_TITLE + ": " + user.getNotificationTime() + "\n" +
                SETTINGS_LANGUAGE_TITLE + ": " + user.getLanguage() + "\n";
    }

    private ExchangeRateDTO getBankDTO(BankExchangeRates bankExchangeRates, String userBank) {
        ExchangeRateDTO exchangeRateDTO = null;
        if (userBank.equalsIgnoreCase(BANK_SETTINGS.get(0))) {
            exchangeRateDTO = bankExchangeRates.getNBU();
        } else if (userBank.equalsIgnoreCase(BANK_SETTINGS.get(1))) {
            exchangeRateDTO = bankExchangeRates.getPrivatBank();
        } else if (userBank.equalsIgnoreCase(BANK_SETTINGS.get(2))) {
            exchangeRateDTO = bankExchangeRates.getMonoBank();
        }
        return exchangeRateDTO;
    }

    private String formatExchangeRate(double exchangeRateDTO, String decimalPlaces) {
        BigDecimal db = new BigDecimal(Double.toString(exchangeRateDTO));
        db = db.setScale(Integer.parseInt(decimalPlaces), RoundingMode.HALF_UP);
        return db.toString().replaceFirst("^0+(?!$)", "");
    }
}
