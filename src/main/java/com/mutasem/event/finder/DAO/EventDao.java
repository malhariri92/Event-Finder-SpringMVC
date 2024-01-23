package com.mutasem.event.finder.DAO;

import com.mutasem.event.finder.models.Event;
import com.mutasem.event.finder.models.Performer;
import com.mutasem.event.finder.models.Venue;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventDao implements IEventDao{

    private final EntityManager entityManager;

    public EventDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Event findEventById(int id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public void save(Event event) {
        entityManager.persist(event);
    }

    @Override
    public void updateEvent(Event event) {
        Venue venue = event.getVenue();
        entityManager.merge(venue);
        event.setVenue(venue);

        List<Performer> performers = new ArrayList<>();
        for(Performer p : event.getPerformers()) {
            performers.add(entityManager.merge(p));
        }
        event.setPerformers(performers);
        entityManager.merge(event);
    }
}
