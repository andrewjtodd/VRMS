package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by andrewtodd on 12/06/2016.
 * An employee of the company. Simply a person.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Employee extends Person {

    String company_id;
}
