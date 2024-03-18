package bot.telegram.currencies.db;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class DefaultConfigGenerator {

    protected static Config generateDefaultConfig() {
        Config defaultConfig = new Config();
        defaultConfig.setDecimalPlaces(2);
        defaultConfig.setBank("ПриватБанк");
        defaultConfig.setCurrencies(new String[]{"USD"});
        defaultConfig.setNotificationTime("09:00");
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
