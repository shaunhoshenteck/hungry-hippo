package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class User extends Person {
    private String address;
    private String city;
    private String state;
    private String zip;

    public User(
            String username,
            String firstName,
            String lastName,
            String email,
            Integer phoneNumber,
            String address,
            String city,
            String state,
            String zip) {
        super(username, firstName, lastName, email, phoneNumber);
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    /** @return true if this User is valid */
    @JsonIgnore
    public boolean isValid() {
        return !username.isEmpty()
                && !firstName.isEmpty()
                && !lastName.isEmpty()
                && !email.isEmpty()
                && Integer.toString(phoneNumber).length() == 10
                && !address.isEmpty()
                && !city.isEmpty()
                && !state.isEmpty()
                && !zip.isEmpty();
    }
}
