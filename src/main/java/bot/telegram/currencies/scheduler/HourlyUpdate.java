package bot.telegram.currencies.scheduler;

import bot.telegram.currencies.db.BankExchangeRates;
import bot.telegram.currencies.db.CurrencyRateDataHelper;

import java.util.Timer;
import java.util.TimerTask;

public class HourlyUpdate {
    public static void getCurrencyRateEveryHour() {
        Timer timer = new Timer();
        BankExchangeRates currencyRates = CurrencyRateDataHelper.loadCurrencyRates();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                CurrencyRateDataHelper.updateCurrencyRates(currencyRates);
            }
        }, 0, 60*60*1000);
    }
}
