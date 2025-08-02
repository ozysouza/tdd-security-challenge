package com.desouza.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desouza.app.entities.Events;

public interface EventRepository extends JpaRepository<Events, Long> {

}
