package Connection;
import java.io.Serializable;
/**
 * This holds the item from the data sets which are scrapped
 * @author vineet
 *
 */

public class ScrappedItem implements Serializable  {
	
	/*Variables to hold variables of items*/
	private String item;
	private String price;
	private String vendor;

	/**
	 * Constructor to take create an object for scrapped item
	 * @param name: Name of the item
	 * @param price: Price of the item
	 * @param vendor: Vendor of the item
	 */
	public ScrappedItem(String name, String price, String vendor){
		this.item = name.trim();
		this.price = price.trim();
		this.vendor = vendor.trim();
	}
	public void printScrappedItem(){
		System.out.println("Item: " + this.getItem());
		System.out.println("Price: " + this.getPrice());
		System.out.println("Vendor: " + this.getVendor());
	}
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getVendor() {
		return vendor;
	}


	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	
}
