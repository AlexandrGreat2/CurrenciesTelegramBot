package bot.telegram.currencies.db;

import bot.telegram.currencies.message.MessageTemplateUKR;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DefaultConfigGenerator {

    public static Config generateDefaultConfig() {
        Config defaultConfig = new Config();
        defaultConfig.setDecimalPlaces(2);
        defaultConfig.setBank(new String("bank3".getBytes(), StandardCharsets.UTF_8));
        defaultConfig.setCurrencies(List.of("USD"));
        defaultConfig.setNotificationTime("09:00");
        defaultConfig.setLanguage(new String("Ukrainian".getBytes(), StandardCharsets.UTF_8));
        return defaultConfig;
    }

    private static void writeConfigToFile(String fileName, Config config) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
