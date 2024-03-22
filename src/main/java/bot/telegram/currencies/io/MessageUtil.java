package bot.telegram.currencies.io;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MessageUtil {


    private MessageUtil() {
    }

    public static final String GREETINGS = new String("Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют".getBytes(), StandardCharsets.UTF_8);
    public static final String GET_INFORMATION = new String("Отримати інфо".getBytes(), StandardCharsets.UTF_8);
    public static final String SETTINGS = new String("Налаштування".getBytes(), StandardCharsets.UTF_8);
    public static final String ACTUAL_INFORMATION = new String("Курс в Приватбанк\nПокупка: 38,81\nПродажа 38,81".getBytes(), StandardCharsets.UTF_8);
    public static final String SETTINGS_DECIMAL_PLACE_TITLE = new String("Кількість знаків після коми".getBytes(), StandardCharsets.UTF_8);
    public static final String SETTINGS_BANK_TITLE = new String("Банк".getBytes(), StandardCharsets.UTF_8);
    public static final String SETTINGS_CURRENCIES_TITLE = new String("Валюти".getBytes(), StandardCharsets.UTF_8);
    public static final String SETTINGS_NOTIFICATION_TIME_TITLE = new String("Час оповіщень".getBytes(), StandardCharsets.UTF_8);
    public static List<String> decimalPlaceSettings = List.of("2", "3", "4");
    public static List<String> bankSettings = List.of(
            new String("НБУ".getBytes(), StandardCharsets.UTF_8),
            new String("ПриватБанк".getBytes(), StandardCharsets.UTF_8),
            new String("Монобанк".getBytes(), StandardCharsets.UTF_8)
    );
    public static List<String> currenciesSettings = List.of("USD", "EUR");
    public static final List<String> SETTINGS_4_ARRAY = List.of(
            "9:00", "10:00", "11:00",
            "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00",
            "18:00", new String("Вимкнути повідомлення".getBytes(), StandardCharsets.UTF_8)
    );


    public static List<String> setDecimalPlaceSettings(UserUtil user) {
        List<String> result = new ArrayList<>();
        String userDecimalPlace = user.getDecimalPlaces();
        for (String decimalPlace : decimalPlaceSettings) {
            if (decimalPlace.equals(userDecimalPlace)) {
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + decimalPlace);
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
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + bank);
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
                result.add(new String("✅ ".getBytes(), StandardCharsets.UTF_8) + currency);
            } else {
                result.add(currency);
            }
        }
        return result;
    }


}
