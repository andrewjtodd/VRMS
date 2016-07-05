package au.com.privitar.domain

/**
 * Created by andrewtodd on 9/06/2016.
 * Class that is basically a location, an address.
 */
class Location {
    String address1;
    String address2;
    String suburb;
    String postcode;
    String state;
    String country;

    @Override
    public String toString() {
        return "Location{" +
                "address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", suburb='" + suburb + '\'' +
                ", postcode='" + postcode + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
