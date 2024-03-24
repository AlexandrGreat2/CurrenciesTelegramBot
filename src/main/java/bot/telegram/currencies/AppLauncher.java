package bot.telegram.currencies;

import bot.telegram.currencies.scheduler.HourlyUpdate;

public class AppLauncher {
    public static void main(String[] args) {
        //update currencies
        HourlyUpdate.getCurrencyRateEveryHour();
    }
}