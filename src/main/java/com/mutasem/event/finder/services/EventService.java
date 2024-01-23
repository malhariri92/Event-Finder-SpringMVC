package com.mutasem.event.finder.services;

import com.mutasem.event.finder.DAO.EventDao;
import com.mutasem.event.finder.models.Event;
import com.mutasem.event.finder.models.Venue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService implements IEventService{

    private final EventDao eventDao;

    public EventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }
    @Override
    public Event findEventById(int id) {
        return eventDao.findEventById(id);
    }

    @Transactional
    @Override
    public void addEvent(Event event) {
        eventDao.save(event);
    }

    @Transactional
    @Override
    public void updateEvent(Event event) {
        eventDao.updateEvent(event);
    }
}
