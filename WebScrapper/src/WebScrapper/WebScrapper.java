package WebScrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;

import Connection.Domhandler;
import Connection.ScrappedItem;

/**
 * Entry Point for Crawler 
 * @author Vineet
 *
 */

public class WebScrapper {

	public static void main(String[] args) {
		
		System.out.println(" Welcome to the Sears Web Scrapper!\n");
		if(args.length < 1 || args.length > 2){
			System.err.println("Usage: java -jar WebScrapper.jar <keyword> [pagenumber]");
			return;
		}
		/* Query 1 type */
		else if(args.length == 1){
				Domhandler data = fetchData(args[0],"1");
				if(data == null){
					System.out.println("No results for the given keyword and page number!");
				} else {
					System.out.println("Results for Keyword: "+ args[0] + " has a total of " +data.getDomSize() + " products");
				}
				
		/* Query 2 type */
		} else if(args.length == 2){
				
			int page = Integer.parseInt(args[1]);
			if(page < 0){
				System.err.println("Error:Page number should be greater than 0");
				return;
			}
			String pageNum = page+ "";
			Domhandler data = fetchData(args[0],pageNum);
			if(data == null){
				System.out.println("No results for the given keyword and page number!");
			} else {
				System.out.println("All the results for given keyword "+ args[0] + " given at page "+ args[1]);
				if(data.getQueryInfo().equals("")){
			    	System.out.println("No results for the given keyword and page number!");
			    	
			    } else {
			    	 for(ScrappedItem element: data.getResults()){
			    		 element.printScrappedItem();
			    	 }
			    }
			 }	
		}		
	}
	/**
	 * 
	 *  Program to fetch data from the HTTP from sears.com and return the result as domhandler object
	 * @param keyword
	 * @param pagenumber
	 * @return domhandler object used to fetch the data
	 */
	private static Domhandler fetchData(String keyword, String pagenumber) {
		
		HttpURLConnection conn = null;
		String keywordnew;
		String pagenumbernew;
		Domhandler domObject;
		try {
			
			keywordnew = URLEncoder.encode(keyword.trim(),"UTF-8");
			pagenumbernew = URLEncoder.encode(pagenumber.trim(),"UTF-8");
			String url = "http://www.sears.com/search="+keywordnew+"?keywordSearch=false&catPrediction=false&previousSort=ORIGINAL_SORT_ORDER&pageNum="+pagenumbernew+"&autoRedirect=false&viewItems=50";
			URL urlobject = new URL(url);
			conn = (HttpURLConnection) urlobject.openConnection();
			conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setReadTimeout(10000);
            conn.connect();
			domObject = new Domhandler(conn.getInputStream());
			return domObject;
			
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error: UnsupportedEncodingException: Error with URLEncoder.encode()");
			 return null;
		} catch(MalformedURLException e) {
			System.out.println("Error: MalformedURLException: Error with URL object string");
			return null;
		} catch (IOException e) {
			System.out.println("Error: IOException: Error with opening connection ");
			return null;
		}
		finally {
			if(conn != null){
				conn.disconnect();
			}
		}
	}

}
