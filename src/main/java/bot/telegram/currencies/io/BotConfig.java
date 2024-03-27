package bot.telegram.currencies.io;

import bot.telegram.currencies.constants.Constants;

import java.io.*;

public class BotConfig {

    private static final String FILE_NAME = "bot_config.json";

    private final String botName;
    private final String botToken;

    public BotConfig(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public static void saveBotConfig(BotConfig botConfig) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            Constants.GSON.toJson(botConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BotConfig loadBotConfig() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            return Constants.GSON.fromJson(reader, BotConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
//generate bot here. Just enter bot Name and BotToken
        BotConfig botConfig = new BotConfig("", "");
        saveBotConfig(botConfig);

        BotConfig loadedBotConfig = loadBotConfig();

        if (loadedBotConfig != null) {
            System.out.println("Bot name: " + loadedBotConfig.getBotName());
            System.out.println("Bot token: " + loadedBotConfig.getBotToken());
        }
    }
}
