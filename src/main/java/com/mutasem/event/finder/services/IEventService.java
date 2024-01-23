package com.mutasem.event.finder.services;

import com.mutasem.event.finder.models.Event;

public interface IEventService {
    Event findEventById(int id);
    void addEvent(Event event);

    void updateEvent(Event event);
}
