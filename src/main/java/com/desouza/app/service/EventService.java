package com.desouza.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desouza.app.dto.EventDTO;
import com.desouza.app.entities.City;
import com.desouza.app.entities.Events;
import com.desouza.app.repositories.CityRepository;
import com.desouza.app.repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public EventDTO insert(EventDTO eventDTO) {
        Events event = new Events();
        event.setDate(eventDTO.getDate());
        event.setName(eventDTO.getName());
        event.setUrl(eventDTO.getUrl());

        City city = cityRepository.getReferenceById(eventDTO.getCityId());
        event.setCity(city);

        event = eventRepository.save(event);
        return new EventDTO(event);
    }

}
