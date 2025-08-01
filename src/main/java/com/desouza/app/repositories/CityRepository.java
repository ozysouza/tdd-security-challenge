package com.desouza.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desouza.app.entities.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
