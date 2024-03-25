package bot.telegram.currencies;

import bot.telegram.currencies.io.TelegramBot;
import bot.telegram.currencies.scheduler.HourlyUpdate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppLauncher {
    public static void main(String[] args) {
        //update currencies
        HourlyUpdate.getCurrencyRateEveryHour();

        //bot running
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(currentDate);
        System.out.println("currentDateTime = " + currentDateTime);
    }
}