package bot.telegram.currencies.exchange.nbuInfo;

import bot.telegram.currencies.constants.Constants;
import bot.telegram.currencies.exchange.BankService;
import bot.telegram.currencies.exchange.ExchangeRateDTO;
import com.google.gson.Gson;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBUService extends BankService {
    private static final String BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final Map<String,Integer> currency;
    public NBUService() {
        currency = new HashMap<>();
        currency.put("UAH", 980);
        currency.put("USD", 840);
        currency.put("EUR", 978);
    }
    @Override
    public ExchangeRateDTO getExchangeRate() {
        HttpResponse<String> response = request(BASE_URL);
        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        List<NBUData> list = List.of(Constants.GSON.fromJson(response.body(), NBUData[].class));
        for (NBUData element: list) {
            if(element.getCurrency() == currency.get("USD")){
                rateDTO.setUSDRateSell(element.getRate());
                rateDTO.setUSDRateBuy(element.getRate());
            }
            if(element.getCurrency() == currency.get("EUR")){
                rateDTO.setEURRateSell(element.getRate());
                rateDTO.setEURRateBuy(element.getRate());
            }
        }
        return rateDTO;
    }
}
