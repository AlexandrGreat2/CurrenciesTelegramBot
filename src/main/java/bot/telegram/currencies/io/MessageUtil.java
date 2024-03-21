package bot.telegram.currencies.io;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {


    private MessageUtil() {
    }

    public static final String GREETINGS = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";
    public static final String GET_INFORMATION = "Отримати інфо";
    public static final String SETTINGS = "Налаштування";
    public static final String ACTUAL_INFORMATION = "Курс в Приватбанк\nПокупка: 38,81\nПродажа 38,81";
    public static final String SETTINGS_DECIMAL_PLACE_TITLE = "Кількість знаків після коми";
    public static final String SETTINGS_BANK_TITLE = "Банк";
    public static final String SETTINGS_CURRENCIES_TITLE = "Валюти";
    public static final String SETTINGS_NOTIFICATION_TIME_TITLE = "Час оповіщень";
    public static List<String> decimalPlaceSettings = List.of("2", "3", "4");
    public static List<String> bankSettings = List.of("НБУ", "ПриватБанк", "Монобанк");
    public static List<String> currenciesSettings = List.of("USD", "EUR");
    public static final List<String> SETTINGS_4_ARRAY = List.of(
            "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "Вимкнути повідомлення"
    );


    public static List<String> setDecimalPlaceSettings(UserUtil user) {
        List<String> result = new ArrayList<>();
        String userDecimalPlace = user.getDecimalPlaces();
        for (String decimalPlace : decimalPlaceSettings) {
            if (decimalPlace.equals(userDecimalPlace)) {
                result.add("✅ " + decimalPlace);
            } else {
                result.add(decimalPlace);
            }
        }

        return result;
    }

    public static List<String> setBankSettings(UserUtil user) {
        List<String> result = new ArrayList<>();
        String userBank = user.getBank();
        for (String bank : bankSettings) {
            if (bank.equals(userBank)) {
                result.add("✅ " + bank);
            } else {
                result.add(bank);
            }
        }

        return result;
    }

    public static List<String> setCurrenciesSettings(UserUtil user) {
        List<String> result = new ArrayList<>();
        List<String> userCurrencies = user.getCurrencies();
        for (String currency : currenciesSettings) {
            if (userCurrencies.contains(currency)) {
                result.add("✅ " + currency);
            } else {
                result.add(currency);
            }
        }
        return result;
    }


}
