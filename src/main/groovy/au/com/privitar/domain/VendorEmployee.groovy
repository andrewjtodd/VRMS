package au.com.privitar.domain

/**
 * Created by andrewtodd on 12/06/2016.
 * An employee of a vendor.
 */
class VendorEmployee extends Person {
    VendorEmployee() {
        super();
    }

    public static final String COLLECTION_NAME = "vendor_employees";
    String vendorName;
    List<HealthScore> scores = new ArrayList<HealthScore>();
    CurrentScore currentScore = new CurrentScore();

    @Override
    public String toString() {
        return "VendorEmployee{" +
                "vendorName='" + vendorName + '\'' +
                ", scores=" + scores +
                ", currentScore=" + currentScore +
                '}';
    }
}
