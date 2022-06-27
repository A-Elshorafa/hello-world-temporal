package monex;

import com.google.gson.Gson;
import jsonParser.MonExResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MonExActivityImpl implements MonExActivity {
    HttpURLConnection connection;
    String line;
    BufferedReader reader;
    StringBuffer responseContent;
    static String result = "";
    @Override
    public String getValue(MonExParams params) {
        try {
//            URL url = new URL("https://api.apilayer.com/currency_data/convert?to="+params.to+"&from="+params.from+"&amount="+params.amount);
            URL url = new URL("https://api.exchangerate.host/convert?from="+params.from+"&to="+params.to+"&amount="+ params.amount);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("apiKey", "aDUVnvxMYWDZZ5WFt0mAjtLOYbTGQ5Bj");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            responseContent = new StringBuffer();
            int status = connection.getResponseCode();

            if(status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
                result = responseContent.toString();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
                Gson g = new Gson();
                String responseString = responseContent.toString();
                MonExResponse responseObj = g.fromJson(responseString, MonExResponse.class);
                result = responseObj.result.toString();
            }
//            result = String.valueOf(18.7 * Double.valueOf(params.amount));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ex: " + ex.getMessage());
        }
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            System.out.println("timer exception: "+ ex.getMessage());
        }
        return result;
    }
}
