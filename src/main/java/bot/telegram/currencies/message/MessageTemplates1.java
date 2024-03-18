package bot.telegram.currencies.message;

public class MessageTemplates1 {
    public static final String GREETING_TEMPLATE = "Привіт, %s! Цей бот допоможе тобі відслідковувати актуальні курси валют!";
    public static final String FAREWELL_TEMPLATE = "Допобачення, %s!";

    public static String getGreetingMessage(String username) {
        return String.format(GREETING_TEMPLATE, username);
    }

    public static String getFarewellTemplate(String username) {
        return String.format(FAREWELL_TEMPLATE, username);
    }
}
