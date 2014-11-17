Author: Vineet Ghatge Hemantkumar
Date: 16th November 2014

Design:
 - Used Jsoup for HTML parsing and it has been included already as part of the jar
 - WebScrapper.jar - This has actual web crawler which takes input from the users and makes HTTP connection using the fetchData(). This scrapes and gives the data from sears.com for about 50 items for the page number.
- Scrapped domObject is passed to getDomSize() or getQueryInfo() depending on whether page number is passed to the function.
- GetDomSize() goes over the document looking for all tag named  - div#nmbProdItems This gives us the total number of product items.
- getQueryInfo() goes over the document looking for vendor, item and price. Jsoup is used for maniuplating the DOM. The items are put an items of class items which stores all the document items that have been obtained.
- These items are then displayed using the printScrappedItem(). Enabled exception handling for cases where there are chances of failure.
   
Files enclosed:
1. WebScrapper.jar
2. WebScrapper folder has the following - Domhandler.java, ScrappedItem.java and WebScrapper.java
3. README.txt - Documentation on the software

Usage Application:
Usage: java -jar WebScrapper.jar <keyword> [pagenumber]
keyword = keyword to search the document
pagenumber = pagenumber to look up documents

Testcases: Here are list of test cases that one can try
1.java -jar WebScrapper.jar
Welcome to the Sears Web Scrapper!
Usage: java -jar WebScrapper.jar <keyword> [pagenumber]

2. java -jar WebScrapper.jar "digital camera"
Welcome to the Sears Web Scrapper!
Results for Keyword: digital camera has a total of 500+ products

3. java -jar WebScrapper.jar "digital camera" 1
Welcome to the Sears Web Scrapper!
All the results for given keyword digital camera given at page 1
Item: Kodak 16-Megapixel PIXPRO Astro Zoom Digital Camera Black
Price: $284.20
Vendor: Sold by Sears
... 49 items listed

4. java -jar WebScrapper.jar "donut" 1
Item: Mobius Donut (CD)
Price: $16.98
Vendor: Sold by Sears
... 49 items listed

5.java -jar WebScrapper.jar "donut" -1
 Welcome to the Sears Web Scrapper!
Error:Page number should be greater than 0

6.java -jar WebScrapper.jar "tablet samsung"
Welcome to the Sears Web Scrapper!
Results for Keyword: tabletsamsung has a total of 500+ products

7. java -jar WebScrapper.jar "vineet"
 Welcome to the Sears Web Scrapper!
Results for Keyword: vineet has a total of 43 products

Future Work:
Include a GUI and enable passing filters in terms available in the scrapped document.
Based on the filters, we will get the required data from website.



