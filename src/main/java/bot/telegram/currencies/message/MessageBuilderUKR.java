package bot.telegram.currencies.message;

import bot.telegram.currencies.db.BankExchangeRates;
import bot.telegram.currencies.db.TelegramUser;
import bot.telegram.currencies.exchange.ExchangeRateDTO;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static bot.telegram.currencies.constants.MessageTemplateUKR.*;


public class MessageBuilderUKR implements MessageBuilder {

    @Override
    public String getGreetings() {
        return GREETINGS;
    }

    @Override
    public String getInformation() {
        return GET_INFORMATION;
    }

    @Override
    public String getSettings() {
        return SETTINGS;
    }

    @Override
    public String getSettingsDecimalPlaceTitle() {
        return SETTINGS_DECIMAL_PLACE_TITLE;
    }

    @Override
    public String getSettingsBankTitle() {
        return SETTINGS_BANK_TITLE;
    }

    @Override
    public String getSettingsCurrenciesTitle() {
        return SETTINGS_CURRENCIES_TITLE;
    }

    @Override
    public String getSettingsNotificationTimeTitle() {
        return SETTINGS_NOTIFICATION_TIME_TITLE;
    }

    @Override
    public String getChoose() {
        return CHOOSE;
    }

    @Override
    public List<String> getDecimalPlaceSettings() {
        return DECIMAL_PLACE_SETTINGS;
    }

    @Override
    public List<String> getBankSettings() {
        return BANK_SETTINGS;
    }

    @Override
    public List<String> getCurrenciesSettings() {
        return CURRENCIES_SETTINGS;
    }

    @Override
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
        result.append(new String("Курс в ".getBytes(), StandardCharsets.UTF_8) + translateBank(userBank) + "\n");
        if (userCurrencies.contains("USD")) {
            result.append(new String("USD покупка: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getUSDRateBuy(), decimalPlaces) + "\n");
            result.append(new String("USD продажа: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getUSDRateSell(), decimalPlaces) + "\n");
        }
        if (userCurrencies.contains("EUR")) {
            result.append(new String("EUR покупка: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getEURRateBuy(), decimalPlaces) + "\n");
            result.append(new String("EUR продажа: ".getBytes(), StandardCharsets.UTF_8)
                    + formatExchangeRate(bankDTO.getEURRateSell(), decimalPlaces) + "\n");
        }
        return result.toString();
    }

    @Override
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

    @Override
    public List<String> setBankSettings(TelegramUser user) {
        List<String> result = new ArrayList<>();
        String userBank = user.getBank();
        for (String bank : BANK_SETTINGS) {
            String bankUKR = translateBank(bank);
            if (bank.equals(userBank)) {
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + bankUKR);
            } else {
                result.add(bankUKR);
            }
        }
        return result;
    }

    private String translateBank(String bank) {
        String result = "";
        switch (bank) {
            case "bank1" : result = new String("НБУ".getBytes(), StandardCharsets.UTF_8);
                break;
            case "bank2" : result = new String("МоноБанк ".getBytes(), StandardCharsets.UTF_8);
                break;
            case "bank3" : result = new String("ПриватБанк".getBytes(), StandardCharsets.UTF_8);
                break;
        }
        return result;
    }

    @Override
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
        return new String("Ідентифікатор користувача: ".getBytes(), StandardCharsets.UTF_8) + user.getUserId() + "\n" +
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
