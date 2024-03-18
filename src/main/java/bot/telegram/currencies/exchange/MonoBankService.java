package bot.telegram.currencies.exchange;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class MonoBankService extends BankService{
    static class MonoJson{
        private int currencyCodeA;
        private int currencyCodeB;
        private long date;
        private float rateSell;
        private float rateBuy;
        private float rateCross;
        public MonoJson(int currencyCodeA, int currencyCodeB, long date, float rateSell, float rateBuy, float rateCross) {
            this.currencyCodeA = currencyCodeA;
            this.currencyCodeB = currencyCodeB;
            this.date = date;
            this.rateSell = rateSell;
            this.rateBuy = rateBuy;
            this.rateCross = rateCross;
        }
        public int getCurrencyCodeA() {
                return currencyCodeA;
            }
        public int getCurrencyCodeB() {
                return currencyCodeB;
            }
        public long getDate() {
                return date;
            }
        public float getRateSell() {
                return rateSell;
            }
        public float getRateBuy() {
                return rateBuy;
            }
        public float getRateCross() {
                return rateCross;
            }
        @Override
        public String toString() {
            return "MonoJson{" +
                    "currencyCodeA=" + currencyCodeA +
                    ", currencyCodeB=" + currencyCodeB +
                    ", date=" + date +
                    ", rateSell=" + rateSell +
                    ", rateBuy=" + rateBuy +
                    ", rateCross=" + rateCross +
                    '}';
        }
    }
    private static final String BASE_URL = "https://api.monobank.ua/bank/currency";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private final Map<String,Integer> currency;
    public MonoBankService() {
        currency = new HashMap<>();
        currency.put("UAH", 980);
        currency.put("USD", 840);
        currency.put("EUR", 978);
    }
    public double[] request(String currencyA, String currencyB, int accuracy) {
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
        MonoJson[] list = new Gson().fromJson(response.body(), MonoJson[].class);
        double[] resultBuySale = new double[2];
        for (MonoJson element: list) {
            if((element.getCurrencyCodeA() == currency.get(currencyA)) && (element.currencyCodeB == currency.get(currencyB))){
                resultBuySale[0] = roundDouble(element.getRateSell(),accuracy);
                resultBuySale[1] = roundDouble(element.getRateBuy(),accuracy);
            }
        }
        return resultBuySale;
    }
}
