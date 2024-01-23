package com.mutasem.event.finder.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "performers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Performer {
    @JsonProperty("id")
    @Id
    @Column(name = "id")
    private int id;
    @JsonProperty("type")
    @Column(name = "type")
    private String type;
    @JsonProperty("name")
    @Column(name = "name")
    private String name;
    @JsonProperty("image")
    @Column(name = "image_url")
    private String imageUrl;
    @JsonProperty("url")
    @Column(name = "ticket_url")
    private String ticketUrl;
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade =  {CascadeType.MERGE, CascadeType.DETACH,
                    CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "event_performers",
            joinColumns = @JoinColumn(name = "performer_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;

    // Default constructor
    public Performer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
