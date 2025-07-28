package com.desouza.app.dto;

import java.time.LocalDate;

import com.desouza.app.entities.Events;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventDTO {

    private Long id;

    @NotBlank(message = "Field is required")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Date must be in the present or in the future")
    private LocalDate date;
    private String url;

    @NotNull(message = "Field is required")
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
