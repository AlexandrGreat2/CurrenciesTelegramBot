package bot.telegram.currencies.db;


import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigDataHelper {

    private static final String ROOT_DIR = "src/main/java/bot/telegram/currencies/";
    private static final String GENERAL_CONFIG_FILE = ROOT_DIR + "config.json";
    //private static final String USER_CONFIG_FILE = ROOT_DIR + "user-data.json";

    public static void main(String[] args) {

        Config defaultConfig = DefaultConfigGenerator.generateDefaultConfig();
        writeConfigToFile(GENERAL_CONFIG_FILE, defaultConfig);


        Config generalConfig = readConfigFromFile(GENERAL_CONFIG_FILE);
        //Config userConfig = readConfigFromFile(USER_CONFIG_FILE);
    }

    private static Config readConfigFromFile(String fileName) {
        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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


