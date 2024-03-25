package bot.telegram.currencies.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserConfigDataHelper {


    private static final String GENERAL_CONFIG_FILE = "src/main/java/bot/telegram/currencies/config.json";
    private static final String USERS_FOLDER = "src/users/";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();



    public static void main(String[] args) {

        Config generalConfig = readConfigFromFile(GENERAL_CONFIG_FILE);

        // Example of creating and saving configuration for the user with ID 123
        Config userConfig = new Config();
        userConfig.setDecimalPlaces(2);
        userConfig.setBank("ПриватБанк");
        userConfig.setCurrencies(List.of("USD"));
        userConfig.setNotificationTime("09:00");

        // Or another way
        Config userConfig2 = DefaultConfigGenerator.generateDefaultConfig();

        // 123 is testing ID of user
        TelegramUser user = new TelegramUser(123, userConfig);
        saveUserConfig(user);

        // The way how we read user config
        Config userConfig3 = loadUserConfig(123).getConfig();

        // Example of reading configuration of user 123
        TelegramUser loadedUser = loadUserConfig(123);
        System.out.println("User 123 Config: " + loadedUser.getConfig());


    }

    private static Config readConfigFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return DefaultConfigGenerator.generateDefaultConfig();
        }
    }

    private static void writeConfigToFile(String fileName, Config config) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUserConfig(TelegramUser user) {
        String userFileName = USERS_FOLDER + user.getUserId() + "_config.json";
        writeConfigToFile(userFileName, user.getConfig());
    }

    public static TelegramUser loadUserConfig(long userId) {
        String userFileName = USERS_FOLDER + userId + "_config.json";
        Config config = readConfigFromFile(userFileName);
        return new TelegramUser(userId, config);
    }
}
