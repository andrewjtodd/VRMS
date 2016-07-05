package au.com.privitar.domain

import javax.persistence.Id

/**
 * Created by andrewtodd on 9/06/2016.
 * Simple person record - will become an employee or something that works for a vendor.
 */
abstract class Person {
    @Id
    String name;
    String role;
    String emailAddress;
    String phoneNumber;
}
