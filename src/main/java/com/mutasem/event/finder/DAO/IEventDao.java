package com.mutasem.event.finder.DAO;

import com.mutasem.event.finder.models.Event;

public interface IEventDao {
    Event findEventById(int id);
    void save(Event event);

    void updateEvent(Event event);
}
