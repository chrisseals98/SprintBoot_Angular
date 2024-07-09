package seals.demo.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class app26a {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private String applicationType;
    private String address;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requestFor", referencedColumnName = "id")
    private user requestor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="application", orphanRemoval = true)
    private Set<document> documents;

    public Long getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public String getApplicationType() {
        return this.applicationType;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public user getRequestor() {
        return this.requestor;
    }

    public Set<document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<document> documents) {
        this.documents = documents;
    }

    public void setRequestor(user requestor) {
        this.requestor = requestor;
    }
}
