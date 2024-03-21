package bot.telegram.currencies.db;

public class TelegramUser {
    private long userId;
    private Config config;

    public TelegramUser(long userId, Config config) {
        this.userId = userId;
        this.config = config;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
