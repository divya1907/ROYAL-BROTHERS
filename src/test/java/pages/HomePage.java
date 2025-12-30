package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private Page page;
    private Locator cityInput;
    private Locator citySuggestion;
    private Locator searchButton;
    private Locator filterPickupDate;
    private Locator filterDropDate;
    private Locator locationFilter;
    private Locator locationOptions;
    private Locator bikeCards;

    public HomePage(Page page) {
        this.page = page;

        // Initialize locators AFTER page is set
        cityInput = page.locator("input[placeholder='Search city']");
        citySuggestion = page.locator(".city-list li");
        searchButton = page.locator("button:has-text('Search')");
        filterPickupDate = page.locator("text=Pickup date");
        filterDropDate = page.locator("text=Dropoff date");
        locationFilter = page.locator("text=Location");
        locationOptions = page.locator(".filters__option");
        bikeCards = page.locator(".vehicle-card");
    }

    public void selectCity(String city) {
        // Wait for the city input to be visible before filling to avoid timing issues
        page.waitForSelector("input[placeholder='Search city']", new Page.WaitForSelectorOptions().setTimeout(15000));
        cityInput.fill(city);

        // Wait for suggestion list to appear and then pick the matching item
        page.waitForSelector(".city-list li", new Page.WaitForSelectorOptions().setTimeout(10000));
        citySuggestion.filter(new Locator.FilterOptions().setHasText(city)).first().click();
    }

    public void clickSearch() {
        searchButton.click();
    }

    public String getPickupDateText() {
        return filterPickupDate.textContent();
    }

    public String getDropDateText() {
        return filterDropDate.textContent();
    }

    public void applyLocationFilter(String location) {
        locationFilter.click();
        locationOptions.filter(new Locator.FilterOptions().setHasText(location)).first().click();
    }

    public List<String> collectBikeData() {
        List<String> data = new ArrayList<>();
        int count = bikeCards.count();
        for (int i = 0; i < count; i++) {
            String model = bikeCards.nth(i).locator(".vehicle-name").textContent();
            String availableAt = bikeCards.nth(i).locator(".vehicle-location").textContent();
            data.add(model + " | " + availableAt);
        }
        return data;
    }
}
