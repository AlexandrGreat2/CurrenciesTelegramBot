package bot.telegram.currencies.message;

import bot.telegram.currencies.db.BankExchangeRates;
import bot.telegram.currencies.db.TelegramUser;
import bot.telegram.currencies.exchange.ExchangeRateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MessageTemplateUKR implements MessageTemplate {

    public final String GREETINGS = new String("Привіт! Цей бот допоможе тобі відслідковувати актуальні курси валют!\nОберіть одну з наступних дій:".getBytes(), StandardCharsets.UTF_8);
    public final String GET_INFORMATION = new String("Отримати інфо".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS = new String("Налаштування".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_DECIMAL_PLACE_TITLE = new String("Кількість знаків після коми".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_BANK_TITLE = new String("Банк".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_CURRENCIES_TITLE = new String("Валюти".getBytes(), StandardCharsets.UTF_8);
    public final String SETTINGS_NOTIFICATION_TIME_TITLE = new String("Час оповіщень".getBytes(), StandardCharsets.UTF_8);
    public final String CHOOSE = new String("Виберіть ".getBytes(), StandardCharsets.UTF_8);
    public final List<String> DECIMAL_PLACE_SETTINGS = List.of("2", "3", "4");
    public final List<String> BANK_SETTINGS = List.of(
            new String("НБУ".getBytes(), StandardCharsets.UTF_8),
            new String("ПриватБанк".getBytes(), StandardCharsets.UTF_8),
            new String("Монобанк".getBytes(), StandardCharsets.UTF_8)
    );
    public final List<String> CURRENCIES_SETTINGS = List.of("USD", "EUR");
    public final List<String> NOTIFICATION_TIME_SETTINGS = List.of(
            "9:00", "10:00", "11:00",
            "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00",
            "18:00", new String("Вимкнути повідомлення".getBytes(), StandardCharsets.UTF_8)
    );

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
    public String getActualInformation(TelegramUser user, BankExchangeRates bankExchangeRates) {
        String userBank = user.getBank();
        String decimalPlaces = user.getDecimalPlaces();
        List<String> userCurrencies = user.getCurrencies();
        ExchangeRateDTO bankDTO = getBankDTO(bankExchangeRates, userBank);

        StringBuilder result = new StringBuilder();
        result.append(new String("Курс в ".getBytes(), StandardCharsets.UTF_8) + userBank + "\n");
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
            if (bank.equals(userBank)) {
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + bank);
            } else {
                result.add(bank);
            }
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
                SETTINGS_NOTIFICATION_TIME_TITLE + ": " + user.getNotificationTime() + "\n";
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
