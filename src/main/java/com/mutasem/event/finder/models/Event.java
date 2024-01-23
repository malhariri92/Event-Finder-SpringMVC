package com.mutasem.event.finder.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="events")
public class Event {

    public Event() {
    }

    @JsonProperty("id")
    @Id
    @Column(name = "id")
    private int id;

    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonProperty("type")
    @Column(name = "type")
    private String type;

    @JsonProperty("datetime_local")
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @JsonProperty("performers")
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
            )
    @JoinTable(
            name = "event_performers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "performer_id")
    )
    List<Performer> performers;

    @JsonProperty("venue")
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "venue_id")
    private Venue venue;

    public String getFormattedDateTime() {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd @h:mm a");
            return dateTime.format(formatter);
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }
}
