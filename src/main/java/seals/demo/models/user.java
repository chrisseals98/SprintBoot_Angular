package seals.demo.models;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class user {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    protected user() {}

    public user(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Map<String, String> getMap() {
        //just testing here
        return Map.of(
            "Applicant Name and Address", getFullName() + "\n123 blueberry lane",
            "Email Address", getEmail(),
            "Name of Applicant Printed", getFullName());
    }
}
