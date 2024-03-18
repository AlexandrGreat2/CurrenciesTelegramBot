package bot.telegram.currencies.exchange;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NBUService extends BankService{
    static class NBUJson{
        private int r030;
        private String txt;
        private float rate;
        private String cc;
        private String exchangedate;

        public NBUJson(int r030, String txt, float rate, String cc, String exchangedate) {
            this.r030 = r030;
            this.txt = txt;
            this.rate = rate;
            this.cc = cc;
            this.exchangedate = exchangedate;
        }

        public int getR030() {
            return r030;
        }

        public String getTxt() {
            return txt;
        }

        public float getRate() {
            return rate;
        }

        public String getCc() {
            return cc;
        }

        public String getExchangedate() {
            return exchangedate;
        }
    }
    private static final String BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private final Map<String,Integer> currency;
    public NBUService() {
        currency = new HashMap<>();
        currency.put("UAH", 980);
        currency.put("USD", 840);
        currency.put("EUR", 978);
    }
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
        NBUJson[] list = new Gson().fromJson(response.body(), NBUJson[].class);
        double[] result = new double[1];
        for (NBUJson element: list) {
            if((element.getR030() == currency.get(currencyA)) && (currency.get(currencyB)==980)){
                result[0] = roundDouble(element.getRate(),accuracy);
            }
        }
        return result;
    }
//    public static void main(String[] args) {
//        NBUService privatBankService = new NBUService();
//        System.out.println(Arrays.toString(privatBankService.request("USD", "UAH", 2)));
//        System.out.println(Arrays.toString(privatBankService.request("EUR","UAH",2)));
//    }
}
