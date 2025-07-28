package com.desouza.app.dto;

import java.time.LocalDate;

import com.desouza.app.entities.Events;

public class EventDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private String url;
    private Long cityId;

    public EventDTO() {
    }

    public EventDTO(Long id, String name, LocalDate date, String url, Long cityId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.url = url;
        this.cityId = cityId;
    }

    public EventDTO(Events entity) {
        id = entity.getId();
        name = entity.getName();
        date = entity.getDate();
        url = entity.getUrl();
        cityId = entity.getCity().getId();
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Long getCityId() {
        return cityId;
    }

}
