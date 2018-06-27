import java.io.IOException;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Main {

	public static void main(String[] args) {

		try {

			System.out.println("Enter a game name, the more specific the better");
			Scanner scanner = new Scanner(System.in);
			String searchString = scanner.nextLine();
			String steamSearchString = searchString.replaceAll(" ", "+");
			String g2aSearchString = searchString.replaceAll(" ", "%20");

			String g2aURL = String.format("https://www.g2a.com/en-gb/search?query=%s", g2aSearchString);
			String steamURL = String.format("https://store.steampowered.com/search/?term=%s", steamSearchString);
			Document steamDoc = Jsoup.connect(steamURL).get();
			steamDoc.select("strike").remove();
			Document g2aDoc = Jsoup.connect(g2aURL).get();

			Elements g2aGameTitle = g2aDoc.select("div.Card__headings");
			Elements g2aDiscountPrice = g2aDoc.select("span.Card__price-cost.price");
			
			Elements steamGameTitle = steamDoc.select("span.title");
			Elements steamDiscountPrice = steamDoc.select("div.col.search_price.discounted.responsive_secondrow");

			System.out.println("STEAM PRICES");
			for (int i = 0; i < steamDiscountPrice.size(); i++) {
				System.out.println("\n" + steamGameTitle.get(i).text() + " ---- "
						+ steamDiscountPrice.get(i).text());
			}

			System.out.println("\n-----------------------------------------");

			System.out.println("\nG2A PRICES");
			for (int i = 0; i < g2aDiscountPrice.size(); i++) {
				System.out.println(
						"\n" + g2aGameTitle.get(i).text() + " ---- " + g2aDiscountPrice.get(i).text());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Your search returned 0 results");
		}

	}
}
