package main;

public class Converter {

	private CurrencyFetcher fetcher;
	
	Converter(){
		
		fetcher = new CurrencyFetcher();
	}
	
	public float convert(float amount, Currency origin, Currency destiny) {
		
		try {
			float dolarsPerOrigin = fetcher.fetch(origin);
			float dolarsPerDestiny = fetcher.fetch(destiny);
			
			return (float)(dolarsPerOrigin * amount / dolarsPerDestiny);
		}
		catch(Exception e) {
			
			System.out.println("Error fetching exchange rates");
			System.out.println(e);
			return 0;
		}
	}
}
