import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class CurrencyConverter {

    // Replace with your actual API key
    private static final String API_KEY = "625cb15dffab0e42008f6cc5";  
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Method to get exchange rate from one currency to another
    public static double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        // Construct the API URL for the request
        String urlStr = BASE_URL + API_KEY + "/latest/" + fromCurrency;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the API response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());

        // Check if the API request was successful
        if (jsonResponse.getString("result").equals("success")) {
            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");
            return conversionRates.getDouble(toCurrency);  // Return the exchange rate for the target currency
        } else {
            throw new Exception("API request failed: " + jsonResponse.getString("error-type"));
        }
    }

    // Method to convert an amount using the exchange rate
    public static double convertAmount(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }

    public static void main(String[] args) {
        try {
            // Example: Convert 100 USD to EUR
            String fromCurrency = "USD";
            String toCurrency = "EUR";
            double amountToConvert = 100; // Example amount
            double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
            
            // Perform conversion
            double convertedAmount = convertAmount(amountToConvert, exchangeRate);
            
            // Display the results
            System.out.println(amountToConvert + " " + fromCurrency + " is equal to " + convertedAmount + " " + toCurrency);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
