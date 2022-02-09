package main;

import java.net.*;
import java.io.*;
import java.util.HashMap;

public class CurrencyFetcher {

	private HashMap<Currency, String> currencyCodes;
	
	CurrencyFetcher(){
		
		currencyCodes = new HashMap<Currency, String>();
		
		currencyCodes.put(Currency.REAL,"BRL");
		currencyCodes.put(Currency.DOLAR,"USD");
		currencyCodes.put(Currency.EURO,"EUR");
		currencyCodes.put(Currency.COROA_TCHECA,"CZK");
		currencyCodes.put(Currency.DOLAR_AUSTRALIANO,"AUD");
		currencyCodes.put(Currency.DOLAR_CANADENCE,"CAD");
		currencyCodes.put(Currency.FRANCO_SUICO,"CHF");
		currencyCodes.put(Currency.IENE,"JPY");
		currencyCodes.put(Currency.LIBRA_ESTERLINA,"GBP");
		currencyCodes.put(Currency.PESO_ARGENTINO,"ARS");
		currencyCodes.put(Currency.YUAN,"CNY");
	}
	
	private float extractExchangeRate(String htmlPage) {
	
		String firstHalf = htmlPage.split("class=\"result__BigRate-sc-1bsijpp-1 iGrAod\">")[1].split("<span class=\"faded-digits\">")[0];
		String secondHalf = htmlPage.split("<span class=\"faded-digits\">")[1].split("</span>")[0]; 
		String stringifiedValue = firstHalf + secondHalf;
		stringifiedValue = stringifiedValue.replace(",", ".");
		
		return Float.parseFloat(stringifiedValue);
	}
	
	public float fetch(Currency currency) throws Exception {
        
		String currencyCode = currencyCodes.get(currency);
		
		URL url = new URL("https://www.xe.com/pt/currencyconverter/convert/?Amount=1&From=" + currencyCode + "&To=USD");
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        
        String htmlPage = "", inputLine;
        while ((inputLine = in.readLine()) != null) 
            htmlPage += inputLine;
        in.close();

        return extractExchangeRate(htmlPage);
	}
}



