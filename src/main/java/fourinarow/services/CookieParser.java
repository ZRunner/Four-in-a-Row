package fourinarow.services;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CookieParser {
	
	public static List<HttpCookie> parseCookies(String cookies_header) {
		List<HttpCookie> cookies = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(cookies_header, ";");
		
		while (tokenizer.hasMoreTokens()) {
			String namevaluePair = tokenizer.nextToken();
			int index = namevaluePair.indexOf('=');
            if (index != -1) {
                String name = namevaluePair.substring(0, index).trim();
                String value = namevaluePair.substring(index + 1).trim();
                cookies.add(new HttpCookie(name, stripOffSurroundingQuote(value)));
            } else {
                // no "=" in name-value pair; it's an error
                throw new IllegalArgumentException("Invalid cookie name-value pair");
            }
		}
		
		return cookies;
	}
	
	private static String stripOffSurroundingQuote(String str) {
        if (str != null && str.length() > 2 &&
            str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
            return str.substring(1, str.length() - 1);
        }
        if (str != null && str.length() > 2 &&
            str.charAt(0) == '\'' && str.charAt(str.length() - 1) == '\'') {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

}
