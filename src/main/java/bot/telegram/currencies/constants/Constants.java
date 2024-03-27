package bot.telegram.currencies.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Constants {
    private Constants() {
    }
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}
