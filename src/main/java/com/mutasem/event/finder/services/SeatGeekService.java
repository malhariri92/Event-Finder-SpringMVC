package com.mutasem.event.finder.services;

import com.mutasem.event.finder.models.Event;
import com.mutasem.event.finder.models.EventResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatGeekService {
    private RestTemplate restTemplate;
    private final String baseUrl = "https://api.seatgeek.com/2/events?per_page=50";
    private LocalDateTime date;
    @Value("${clientId}")
    private String clientId;
    public SeatGeekService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
        this.date = LocalDateTime.now();
    }

    public List<Event> allEvents(String query) {
        try {
            String url = baseUrl + clientId + "&datetime_local.gt=" + date.toString();
            if(query != null && !query.isEmpty()) {
                url += "&q=" + query;
            }
            List<Event> events = new ArrayList<>();

            for(int page = 1; page < 6; page++) {
                url += "&page=" + page;
                List<Event> pageEvents = new ArrayList<>();
                ResponseEntity<EventResponse> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<EventResponse>() {});
                EventResponse eventResponse = responseEntity.getBody();

                if (eventResponse != null) {
                   pageEvents.addAll(eventResponse.getEvents());
                }
                if(!pageEvents.isEmpty()) {
                    events.addAll(pageEvents);
                }
            }
            return events;

        }catch(HttpClientErrorException | HttpServerErrorException ex) {
            return null;
        }

    }

    public Event getEventById(int id) {
        try{
            ResponseEntity<EventResponse> responseEntity = restTemplate.exchange(
                    baseUrl + "&id=" + id + clientId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<EventResponse>() {});
            EventResponse eventResponse = responseEntity.getBody();
            if (eventResponse.getEvents() != null && ! eventResponse.getEvents().isEmpty()) {
                return eventResponse.getEvents().get(0);
            } else {
                return null;
            }
        }catch(HttpClientErrorException | HttpServerErrorException ex) {
            return null;
        }
    }

    public List<Event> filterEvents(String selectedCategory, List<Event> events) {
        if(selectedCategory == null) return events;
        List<Event> filteredEvents = new ArrayList<>();
        for(Event e : events) {
            if(e.getType().toLowerCase().contains(selectedCategory.toLowerCase())) {
                filteredEvents.add(e);
            }
        }
        System.out.println(filteredEvents.size());
        return filteredEvents;
    }
}
