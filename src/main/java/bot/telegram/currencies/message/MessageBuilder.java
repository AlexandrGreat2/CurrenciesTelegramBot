package bot.telegram.currencies.message;

import bot.telegram.currencies.db.Config;
import bot.telegram.currencies.db.TelegramUser;

public class MessageBuilder {
    public String buildStartMessage(TelegramUser user) {
        Config userConfig = user.getConfig();
        return "Ласкаво просимо! " + userConfig.getBank();
    }

    public String buildOptionsMessage(TelegramUser user) {
        Config userConfig = user.getConfig();
        return "Ви можете змінити налаштування в меню опцій. Доступні валюти: " + String.join(", ", userConfig.getCurrencies());
    }

    public String buildExitMessage(TelegramUser user) {
        return "Дякуємо! До побачення!";
    }
}

