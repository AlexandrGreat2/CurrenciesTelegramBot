package bot.telegram.currencies.message;

import bot.telegram.currencies.db.Config;
import bot.telegram.currencies.db.TelegramUser;
import static bot.telegram.currencies.db.UserConfigDataHelper.saveUserConfig;


public class MessageGeneration {
    public static void main(String[] args) {
        // Припустимо, що у нас є конфігурація для користувача
        Config userConfig = new Config();
        userConfig.setDecimalPlaces(2);
        userConfig.setBank("PrivatBank");
        userConfig.setCurrencies(new String[]{"USD", "EUR", "GBP"});
        userConfig.setNotificationTime("09:00");

        //Тестовий користувач
        TelegramUser user = new TelegramUser(123, userConfig);
        saveUserConfig(user);

        // Створюємо екземпляр MessageBuilder
        MessageBuilder messageBuilder = new MessageBuilder();

        // Будуємо повідомлення для старту
        String startMessage = messageBuilder.buildStartMessage(user);
        System.out.println("Start message: " + startMessage);

        // Будуємо повідомлення для опцій
        String optionsMessage = messageBuilder.buildOptionsMessage(user);
        System.out.println("Options message: " + optionsMessage);

        // Будуємо повідомлення для виходу
        String exitMessage = messageBuilder.buildExitMessage(user);
        System.out.println("Exit message: " + exitMessage);
    }
}
