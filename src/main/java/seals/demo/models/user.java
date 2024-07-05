package seals.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="users")
public class user {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    @JsonProperty("first_name")
    private String first_name;
    @JsonProperty("last_name")
    private String last_name;
    private String email;

    protected user() {}

    public user(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirst_Name() {
        return this.first_name;
    }

    public String getLast_Name() {
        return this.last_name;
    }

    public String getEmail() {
        return this.email;
    }
}
