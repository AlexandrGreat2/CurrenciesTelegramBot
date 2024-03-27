package bot.telegram.currencies.constants;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageTemplateUKR {
    private MessageTemplateUKR() {
    }
    public static final String GREETINGS = new String("Привіт! Цей бот допоможе тобі відслідковувати актуальні курси валют!\nОберіть одну з наступних дій:".getBytes(), StandardCharsets.UTF_8);
    public static  final String GET_INFORMATION
            = new String("Отримати інфо".getBytes(), StandardCharsets.UTF_8);
    public static  final String SETTINGS = new String("Налаштування".getBytes(), StandardCharsets.UTF_8);
    public static  final String SETTINGS_DECIMAL_PLACE_TITLE = new String("Кількість знаків після коми".getBytes(), StandardCharsets.UTF_8);
    public static  final String SETTINGS_BANK_TITLE = new String("Банк".getBytes(), StandardCharsets.UTF_8);
    public static  final String SETTINGS_CURRENCIES_TITLE = new String("Валюти".getBytes(), StandardCharsets.UTF_8);
    public static  final String SETTINGS_NOTIFICATION_TIME_TITLE = new String("Час оповіщень".getBytes(), StandardCharsets.UTF_8);
    public static  final String SETTINGS_LANGUAGE_TITLE = new String("Мова".getBytes(), StandardCharsets.UTF_8);

    public static  final String CHOOSE = new String("Виберіть ".getBytes(), StandardCharsets.UTF_8);
    public static  final List<String> DECIMAL_PLACE_SETTINGS = List.of("2", "3", "4");
    public static  final List<String> BANK_SETTINGS = List.of(
            new String("bank1".getBytes(), StandardCharsets.UTF_8),
            new String("bank2".getBytes(), StandardCharsets.UTF_8),
            new String("bank3".getBytes(), StandardCharsets.UTF_8)
    );
    public static  final List<String> CURRENCIES_SETTINGS = List.of("USD", "EUR");
    public static  final List<String> NOTIFICATION_TIME_SETTINGS = List.of(
            "9:00", "10:00", "11:00",
            "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00",
            "18:00", new String("Вимкнути повідомлення".getBytes(), StandardCharsets.UTF_8)
    );
    public static  final List<String> LANGUAGE_SETTINGS = List.of(
            new String("Ukrainian".getBytes(), StandardCharsets.UTF_8),
            new String("English".getBytes(), StandardCharsets.UTF_8));
}
