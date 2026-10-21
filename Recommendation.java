package areabusiness;

public class Recommendation {

    public static String getSuggestion(String areaType, double budget) {

        areaType = areaType.toLowerCase();

        if (areaType.equals("college")) {

            if (budget <= 100000) {
                return "Stationery / Xerox & Printing Shop";
            } else {
                return "Cafe / Fast Food Outlet";
            }

        } else if (areaType.equals("residential")) {

            if (budget <= 150000) {
                return "Laundry / Bakery";
            } else {
                return "Grocery Store / Pharmacy";
            }

        } else if (areaType.equals("railway station")) {

            if (budget <= 100000) {
                return "Tea & Coffee Stall";
            } else {
                return "Convenience Store / Food Outlet";
            }

        } else if (areaType.equals("market")) {

            if (budget <= 200000) {
                return "Cosmetics / Footwear Shop";
            } else {
                return "Clothing / Electronics Store";
            }

        } else if (areaType.equals("office")) {

            if (budget <= 150000) {
                return "Printing / Stationery Shop";
            } else {
                return "Lunch Centre / Cafe";
            }

        } else if (areaType.equals("tourist")) {

            if (budget <= 200000) {
                return "Fast Food / Souvenir Shop";
            } else {
                return "Restaurant / Travel Agency";
            }

        } else {

            return "General Store / Food Outlet";
        }
    }
}
