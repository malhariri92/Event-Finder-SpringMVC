package com.mutasem.event.finder.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "venues")
public class Venue {
    @JsonProperty("id")
    @Id
    @Column(name = "id")
    private int id;
    @JsonProperty("name")
    @Column(name = "name")
    private String name;
    @JsonProperty("state")
    @Column(name = "state")
    private String state;
    @JsonProperty("city")
    @Column(name = "city")
    private String city;
    @JsonProperty("address")
    @Column(name = "address")
    private String address;
    @JsonProperty("extended_address")
    @Column(name = "extended_address")
    private String extendedAddress;

    public Venue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExtendedAddress() {
        return extendedAddress;
    }

    public void setExtendedAddress(String extendedAddress) {
        this.extendedAddress = extendedAddress;
    }
}
