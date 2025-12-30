package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import pages.HomePage;

import java.util.List;

public class RoyalBrothersTest extends BaseTest {

    @Test
    void validateBikeAvailabilityFlow() {
        page.navigate("https://www.royalbrothers.com"); // Navigate first
        HomePage home = new HomePage(page);

        String city = "Bangalore";
        String location = "Indiranagar";

        home.selectCity(city);
        Assertions.assertTrue(page.url().contains("royalbrothers"));

        home.clickSearch();
        Assertions.assertNotNull(home.getPickupDateText());
        Assertions.assertNotNull(home.getDropDateText());

        home.applyLocationFilter(location);

        List<String> bikeData = home.collectBikeData();
        Assertions.assertFalse(bikeData.isEmpty(), "Bike list should not be empty");

        for (String bike : bikeData) {
            System.out.println(bike);
            Assertions.assertTrue(bike.contains(location), "Bike not available at selected location");
        }
    }
}
