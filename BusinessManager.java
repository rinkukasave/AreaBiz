package areabusiness;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class BusinessManager {

    private final ArrayList<Business> businesses;
    private final PropertyChangeSupport changeSupport;

    public BusinessManager() {
        businesses = new ArrayList<>();
        changeSupport = new PropertyChangeSupport(this);
    }

    // Register listener for automatic refresh
    public void addChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    // Remove listener
    public void removeChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    // Notify dashboard and other panels
    private void notifyBusinessChange() {
        changeSupport.firePropertyChange("businesses", false, true);
    }

    // Add business
    public void addBusiness(Business business) {
        if (business != null) {
            businesses.add(business);
            notifyBusinessChange();
        }
    }

    // Get all businesses
    public ArrayList<Business> getAllBusinesses() {
        return businesses;
    }

    // Search business by name
    public ArrayList<Business> searchByName(String name) {
        ArrayList<Business> result = new ArrayList<>();

        for (Business business : businesses) {
            if (business.getBusinessName() != null
                    && business.getBusinessName().equalsIgnoreCase(name)) {
                result.add(business);
            }
        }

        return result;
    }

    // Search businesses by area
    public ArrayList<Business> searchByArea(String area) {
        ArrayList<Business> result = new ArrayList<>();

        for (Business business : businesses) {
            if (business.getArea() != null
                    && business.getArea().equalsIgnoreCase(area)) {
                result.add(business);
            }
        }

        return result;
    }

    // Delete business by ID
    public boolean deleteBusiness(int businessId) {
        for (int i = 0; i < businesses.size(); i++) {
            if (businesses.get(i).getBusinessId() == businessId) {
                businesses.remove(i);
                notifyBusinessChange();
                return true;
            }
        }

        return false;
    }

    // Update business by ID
    public boolean updateBusiness(int businessId, Business updatedBusiness) {
        if (updatedBusiness == null) {
            return false;
        }

        for (int i = 0; i < businesses.size(); i++) {
            if (businesses.get(i).getBusinessId() == businessId) {
                businesses.set(i, updatedBusiness);
                notifyBusinessChange();
                return true;
            }
        }

        return false;
    }

    // Calculate total monthly sales
    public double getTotalMonthlySales() {
        double total = 0;

        for (Business business : businesses) {
            total += business.getMonthlySales();
        }

        return total;
    }

    // Count businesses
    public int getBusinessCount() {
        return businesses.size();
    }

    // Count unique areas
    public int getAreaCount() {
        ArrayList<String> areas = new ArrayList<>();

        for (Business business : businesses) {
            String area = business.getArea();

            if (area != null && !areas.contains(area)) {
                areas.add(area);
            }
        }

        return areas.size();
    }
}