/**
 *
 *  @author Potera Patryk S22683
 *
 */

package zad1;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class Service {

    private String countryCode;
    private String localCurrency;
    private String city;

    public Service(String country) {

        Map<String, String> countries = new HashMap<>();
        String[] isoCountries = Locale.getISOCountries();
        for (String isoCountry : isoCountries) {
            countries.put(new Locale("", isoCountry).getDisplayCountry(), isoCountry);
        }
        this.countryCode = countries.get(country);
        this.localCurrency = Currency.getInstance(new Locale("", countryCode)).getCurrencyCode();

    }

    public String getWeather(String city) {
        this.city=city;
        String text = null;
        try {
            text = request("https://api.openweathermap.org/data/2.5/weather?q="+city+","+countryCode+"&appid=576f381a342ac424360b9f06330b4e51");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private String request(String stringURL) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        URL url = new URL(stringURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = bufferedReader.readLine();
        while (line!=null){
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        request.disconnect();
        bufferedReader.close();
        return String.valueOf(stringBuilder);
    }

    public Double getRateFor(String currency){
        String text;
        JSONObject json = null;
        if (!localCurrency.equals(currency)) {
            try {
                text = request("https://api.exchangerate.host/convert?from=" + localCurrency + "&to=" + currency + "");
                JSONParser jsonParser = new JSONParser();
                json = (JSONObject) jsonParser.parse(text);
//                System.out.println(json.get("result"));
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return (Double) json.get("result");
        }
        return 1.0;
    }

    String weatherInfo(String city){
        String text = getWeather(city);
        JSONParser jsonParser = new JSONParser();
        JSONObject json = null;
        JSONObject jsonWeather = null;
        JSONObject jsonMain = null;
        String tmp;
        try {
            json = (JSONObject) jsonParser.parse(text);
            tmp = json.get("weather").toString().replace("[","").replace("]","");
            jsonWeather = (JSONObject) jsonParser.parse(tmp);
            tmp = json.get("main").toString().replace("[","").replace("]","");
            jsonMain = (JSONObject) jsonParser.parse(tmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tmp = jsonMain.get("temp").toString();
        double temp = Double.parseDouble(tmp);
        temp -= 273.15;
        DecimalFormat df = new DecimalFormat("###.#");

        String r = "<html>" +
                "<br/>Loc: "+city+
                "<br/>Sky:"+jsonWeather.get("description")+
                "<br/>Temperature:"+df.format(temp)+
                "</html>";
        return r;
    }

    public Double getNBPRate() {
        if (!localCurrency.equals("PLN")) {
            try {
                URL url = new URL("https://www.nbp.pl/kursy/kursya.html");
                for (int i = 0; i < 2; i++) {

                try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                    String line;
                    while ((line = in.readLine()) != null)
                        if (line.contains(localCurrency)) {
                            line = line.replace("<td class=\"right\">", "");
                            line = line.replace("</td>", "");
                            line = line.trim();
                            if (line.contains(localCurrency)) {
                                String[] s = line.split(" ");
                                double waluta = Double.parseDouble(s[0]);
                                line = in.readLine();
                                line = line.replace("<td class=\"right\">", "");
                                line = line.replace("</td>", "");
                                double kursSredni = Double.parseDouble(line.replace(",", "."));
                                return waluta / kursSredni;
                            }
                        }
                }
                    url = new URL("https://www.nbp.pl/kursy/kursyb.html");
            }
                } catch(IOException e){
                    e.printStackTrace();
                }
            return -1.0;
            }
        return 1.0;
    }

}  
