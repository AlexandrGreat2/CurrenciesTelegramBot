package bot.telegram.currencies.exchange.privatebankInfo;

import bot.telegram.currencies.constants.Constants;
import bot.telegram.currencies.exchange.BankService;
import bot.telegram.currencies.exchange.ExchangeRateDTO;
import com.google.gson.Gson;
import java.net.http.HttpResponse;
import java.util.List;

public class PrivatBankService extends BankService {
    private static final String BASE_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
    @Override
    public ExchangeRateDTO getExchangeRate() {
        HttpResponse<String> response = request(BASE_URL);
        ExchangeRateDTO rateDTO = new ExchangeRateDTO();
        List<PrivatBankData> list = List.of(Constants.GSON.fromJson(response.body(), PrivatBankData[].class));
        for (PrivatBankData element: list) {
            if(element.getCurrencyFrom().equals("USD") && element.getCurrencyTo().equals("UAH")){
                rateDTO.setUSDRateSell(element.getRateSell());
                rateDTO.setUSDRateBuy(element.getRateBuy());
            }
            if(element.getCurrencyFrom().equals("EUR") && element.getCurrencyTo().equals("UAH")){
                rateDTO.setEURRateSell(element.getRateSell());
                rateDTO.setEURRateBuy(element.getRateBuy());
            }
        }
        return rateDTO;
    }
}
