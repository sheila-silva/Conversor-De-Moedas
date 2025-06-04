import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExchangeRateService {
    private static final String API_KEY = "285510a3f0c150c08a09e626";
    private Set<String> supportedCurrencies;

    public ExchangeRate getExchangeRate(String from, String to) {
        try {
            String url = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", API_KEY, from);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject conversionRates = json.getAsJsonObject("conversion_rates");
            double rate = conversionRates.get(to).getAsDouble();

            return new ExchangeRate(new Currency(from), new Currency(to), rate);
        } catch (Exception e) {
            System.err.println("Erro ao buscar taxa de câmbio: " + e.getMessage());
            return new ExchangeRate(new Currency(from), new Currency(to), 0);
        }
    }

    public boolean isValidCurrencyCode(String code) {
        try {
            if (supportedCurrencies == null) {
                String url = String.format("https://v6.exchangerate-api.com/v6/%s/latest/USD", API_KEY);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject conversionRates = json.getAsJsonObject("conversion_rates");

                supportedCurrencies = conversionRates.keySet();
            }
            return supportedCurrencies.contains(code);
        } catch (Exception e) {
            System.err.println("Erro ao validar código de moeda: " + e.getMessage());
            return false;
        }
    }
}
