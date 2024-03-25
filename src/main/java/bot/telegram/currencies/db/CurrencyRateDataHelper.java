package bot.telegram.currencies.db;

import bot.telegram.currencies.exchange.ExchangeRateDTO;
import bot.telegram.currencies.exchange.monobankInfo.MonoBankService;
import bot.telegram.currencies.exchange.nbuInfo.NBUService;
import bot.telegram.currencies.exchange.privatebankInfo.PrivatBankService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CurrencyRateDataHelper {

    private static final String ROOT_DIR = "src/main/java/bot/telegram/currencies/";
    private static final String FILENAME = ROOT_DIR + "currencies-cache.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    public static void main(String[] args) {
//        //test to be sure that all is right
//        BankExchangeRates currencyRates = loadCurrencyRates();
//
//        updateCurrencyRates(currencyRates);
//
//        saveCurrencyRates(currencyRates);
//
//        printCurrencyRates(currencyRates);
//    }

    public static BankExchangeRates loadCurrencyRates() {
        try (FileReader reader = new FileReader(FILENAME)) {
            return gson.fromJson(reader, BankExchangeRates.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new BankExchangeRates();
        }
    }

    private static void saveCurrencyRates(BankExchangeRates currencyRates) {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            gson.toJson(currencyRates, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateCurrencyRates(BankExchangeRates currencyRates) {
        MonoBankService monoBankService = new MonoBankService();
        ExchangeRateDTO monoBankRates = monoBankService.getExchangeRate();

        NBUService nbuService = new NBUService();
        ExchangeRateDTO nbuRates = nbuService.getExchangeRate();

        PrivatBankService privatBankService = new PrivatBankService();
        ExchangeRateDTO privatBankRates = privatBankService.getExchangeRate();

        currencyRates.setNBU(nbuRates);
        currencyRates.setPrivatBank(privatBankRates);
        currencyRates.setMonoBank(monoBankRates);

        saveCurrencyRates(currencyRates);
    }

    private static void printCurrencyRates(BankExchangeRates currencyRates) {
        System.out.println("NBU Exchange Rates: ");
        System.out.println(currencyRates.getNBU());

        System.out.println("PrivatBank Exchange Rates: ");
        System.out.println(currencyRates.getPrivatBank());

        System.out.println("MonoBank Exchange Rates: ");
        System.out.println(currencyRates.getMonoBank());
    }
}