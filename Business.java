package areabusiness;
import java.io.Serializable;

public class Business implements Serializable   {

    private int businessId;
    private String businessName;
    private String ownerName;
    private String businessType;
    private String area;
    private String contact;
    private double investment;
    private double monthlySales;
    private String areaType;
    private String category;

    public Business(int businessId, String businessName,
                    String ownerName, String businessType,
                    String area, String contact,
                    double investment, double monthlySales,
                    String areaType, String category) {

        this.businessId = businessId;
        this.businessName = businessName;
        this.ownerName = ownerName;
        this.businessType = businessType;
        this.area = area;
        this.contact = contact;
        this.investment = investment;
        this.monthlySales = monthlySales;
        this.areaType = areaType;
        this.category = category;
    }

    public int getBusinessId() {
        return businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getArea() {
        return area;
    }

    public String getContact() {
        return contact;
    }

    public double getInvestment() {
        return investment;
    }

    public double getMonthlySales() {
        return monthlySales;
    }

    public String getAreaType() {
        return areaType;
    }

    public String getCategory() {
        return category;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setInvestment(double investment) {
        this.investment = investment;
    }

    public void setMonthlySales(double monthlySales) {
        this.monthlySales = monthlySales;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}