package bot.telegram.currencies.exchange.monobankInfo;

import bot.telegram.currencies.constants.Constants;
import bot.telegram.currencies.exchange.BankService;
import bot.telegram.currencies.exchange.ExchangeRateDTO;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonoBankService extends BankService {
    private static final String BASE_URL = "https://api.monobank.ua/bank/currency";
    private final Map<String,Integer> currency;
    public MonoBankService() {
        currency = new HashMap<>();
        currency.put("UAH", 980);
        currency.put("USD", 840);
        currency.put("EUR", 978);
    }
    public ExchangeRateDTO getExchangeRate() {
        HttpResponse<String> response = request(BASE_URL);
        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        List<MonoBankData> list = List.of(Constants.GSON.fromJson(response.body(), MonoBankData[].class));
        for (MonoBankData element: list) {
            if((element.getCurrencyFrom() == currency.get("USD")) && (element.getCurrencyTo() == currency.get("UAH"))){
                rateDTO.setUSDRateSell(element.getRateSell());
                rateDTO.setUSDRateBuy(element.getRateBuy());
            }
            if((element.getCurrencyFrom() == currency.get("EUR")) && (element.getCurrencyTo() == currency.get("UAH"))){
                rateDTO.setEURRateSell(element.getRateSell());
                rateDTO.setEURRateBuy(element.getRateBuy());
            }
        }
        return rateDTO;
    }
}
