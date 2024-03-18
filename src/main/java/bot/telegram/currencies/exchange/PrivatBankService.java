package bot.telegram.currencies.exchange;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class PrivatBankService extends BankService{
    static class PrivatJson{
    private String ccy;
    private String base_ccy;
    private float buy;
    private float sale;

    public PrivatJson(String ccy, String base_ccy, float buy, float sale) {
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }

    public String getCcy() {
        return ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public float getBuy() {
        return buy;
    }

    public float getSale() {
        return sale;
    }
}
    private static final String BASE_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    @Override
    double[] request(String currencyA, String currencyB, int accuracy) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        PrivatJson[] list = new Gson().fromJson(response.body(), PrivatJson[].class);
        double[] resultBuySale = new double[2];
        for (PrivatJson element: list) {
            if(element.getCcy().equals(currencyA) && element.getBase_ccy().equals(currencyB)){
                resultBuySale[0] = roundDouble(element.getSale(),accuracy);
                resultBuySale[1] = roundDouble(element.getBuy(),accuracy);
            }
        }
        return resultBuySale;
    }
}
