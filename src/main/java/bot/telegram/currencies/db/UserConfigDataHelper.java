package bot.telegram.currencies.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class UserConfigDataHelper {

//    private static final String ROOT_DIR = "src/main/java/bot/telegram/currencies/";
    private static final String GENERAL_CONFIG_FILE = "src/main/java/bot/telegram/currencies/config.json";
    private static final String USERS_FOLDER = "src/users/";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    private static final Map<Long, TelegramUser> users = new HashMap<>();

    public static void main(String[] args) {
        // Завантаження загальної конфігурації
        Config generalConfig = readConfigFromFile(GENERAL_CONFIG_FILE);

        // Приклад створення та збереження конфігурації для користувача з ID 123
        Config userConfig = new Config();
        userConfig.setDecimalPlaces(2);
        userConfig.setBank("ПриватБанк");
        userConfig.setCurrencies(new String[]{"USD"});
        userConfig.setNotificationTime("09:00");
        // Або можна зрбобити так
        Config userConfig2 = DefaultConfigGenerator.generateDefaultConfig();

        // 123 це тестовий id користувача
        TelegramUser user = new TelegramUser(123, userConfig);
        saveUserConfig(user);

        // Ось так ми читаємо конфіг з файлу
        Config userConfig3 = loadUserConfig(123).getConfig();

        // Приклад зчитування конфігурації для користувача з ID 123
        TelegramUser loadedUser = loadUserConfig(123);
        System.out.println("User 123 Config: " + loadedUser.getConfig());
    }

    private static Config readConfigFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeConfigToFile(String fileName, Config config) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUserConfig(TelegramUser user) {
        String userFileName = USERS_FOLDER + user.getUserId() + "_config.json";
        writeConfigToFile(userFileName, user.getConfig());
    }

    private static TelegramUser loadUserConfig(long userId) {
        String userFileName = USERS_FOLDER + userId + "_config.json";
        Config config = readConfigFromFile(userFileName);
        return new TelegramUser(userId, config);
    }
}
