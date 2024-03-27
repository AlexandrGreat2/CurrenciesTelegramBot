package bot.telegram.currencies;

import bot.telegram.currencies.io.BotConfig;
import bot.telegram.currencies.io.TelegramBot;
import bot.telegram.currencies.scheduler.HourlyUpdate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppLauncher {
    public static void main(String[] args) {
        //update currencies and regenerate user config
        HourlyUpdate.getAndSendEveryHour();

        //put your bot name and token
        BotConfig.saveBotConfig(new BotConfig("DenisProjectBot", "7126409067:AAFAX1wz5VPHr0GcXBL2__5PvCmH4qJW3eU"));

        //bot running
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}