package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;

/**
 * 
 * This acts on the dom objects of the scrapped documents from the web scrapper
 * @author Vineet
 *
 */

public class Domhandler {
	
	/**
	 * Data Structures to store query results
	 */
	private Set<ScrappedItem> results;
	private String size;
	private Document dom;
	
	/* Scrapped Item getter and setter functions */
	public Set<ScrappedItem> getResults() {
		return results;
	}

	public void setResults(Set<ScrappedItem> results) {
		this.results = results;
	}
	
	public Domhandler(InputStream ips){
		this.results = new HashSet<ScrappedItem>();
		this.size = null;
		convertDomToData(ips);
	}
	
	/**
	 * Convert InputStream data into DOM object
	 * @return: Return void dom object
	 */
	private void convertDomToData(InputStream ips) {
		
		StringBuilder sb = new StringBuilder();
		this.results.clear();
		this.size = null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(ips));
		try {
			String nextline = br.readLine();
			 while(nextline!=null){
		            if(!nextline.trim().equals(""))
		            {
		                sb.append(nextline).append("\n");
		            }
		            nextline = br.readLine();
		      }
			 
			 dom = (Document) Jsoup.parse(sb.toString());
			 
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Function to return the dom object size from the document
	 * @return: String of the dom object size
	 */
	public String getDomSize(){
		
		String size = "";
		if(this.size == null){
			Elements number = this.dom.select("div#nmbProdItems");
			//System.out.println("No of results:" + number.toString());
			if(!number.isEmpty()){
				size = number.first().text();
			}
			String regex = "\\d+\\s*\\+?$";
			Matcher match = Pattern.compile(regex).matcher(size);
			if(match.find()){
				size = match.group(0);
			}
			
			if(size.equals(""))
				this.size = "0";
			else
				this.size = size;
			
		}
		return this.size;
	}
	/**
	 * Function to get information extracted from the document
	 * @return: results object type
	 */
	public Object getQueryInfo() {
		 if(this.results.isEmpty()){
			 Elements products = this.dom.select("div.cardContainer.addToCartEnabled"); 
			 for(Element element: products){
				 
				 Document doc = Jsoup.parse(element.toString());
				 //System.out.println(doc);
				 String item = doc.select("div.cardContainer.addToCartEnabled > div.cardInner > div.cardProdTitle > h2 > a").text();
				 String vendor = doc.select("div.cardContainer.addToCartEnabled > div.cardInner > div#mrkplc > p:not([class])").text();
				 //System.out.println(vendor);
				 if (vendor.equals("")){
					 vendor = "Sold by Sears";
				 }
				 String price = null;
				 Element id = doc.select("div.cardContainer.addToCartEnabled > div.cardInner > div.SGLView").first();
				 //System.out.println(id);
				 if(id == null)
				 {
					 String tree = "div.cardContainer.addToCartEnabled > div.cardInner > div.cPP_v2 > span.price_v2";
					 price = doc.select(tree).text();
					 
				 } else {
					 
					 String tree = doc.select("div.cardContainer.addToCartEnabled > div.cardInner > input#pId").attr("value");
					 //System.out.println(tree);
					 String maintree = "div.cardContainer.addToCartEnabled > div.cardInner > div.SGLView > ";
					 String finalprice = maintree + "div#ss_" + tree + " > span.price_v2";
					 //System.out.println(finalprice);
					 price = doc.select(finalprice).text();
				 }
				 
				 
				 if(!price.equals("") || !item.equals("")){
					 this.results.add(new ScrappedItem(item, price, vendor));
				 }
			}
		}
		 //System.out.println(this.results);
		 return this.results.toString();
	}
}
