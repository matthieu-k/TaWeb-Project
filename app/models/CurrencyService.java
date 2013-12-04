package models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class CurrencyService {

	public static void getCurrency(String currency) {
		String xmlStr = "";
        try {
        	xmlStr = Core.readUrl("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
        	Document doc = Jsoup.parse(xmlStr, "", Parser.xmlParser());
    		Element cube = doc.getElementById("Cube");
    		System.out.println(cube.toString());
        } catch (Exception e) {
		e.printStackTrace();
        }
	}
}
//    		for (Element c : cube3) {
//    			c.ge
//    		}
    		
//            Namespace ns = Namespace.getNamespace("http://www.ecb.int/vocabulary/2002-08-01/eurofxref");
//            
//            
//            Element cube = racine.getChild("Cube", ns);
//            
//            Element cube2 = cube.getChild("Cube", ns);
//            List<Element> cube3 = cube2.getChildren("Cube", ns);
//            for (Element element : cube3) {
//                if(element.getAttributeValue("currency").equals(currencyCode)) {
//                    rate = Double.parseDouble(element.getAttributeValue("rate"));
//                }
//            }
//            return rate * amount;

