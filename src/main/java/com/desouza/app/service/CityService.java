package com.desouza.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desouza.app.dto.CityDTO;
import com.desouza.app.entities.City;
import com.desouza.app.repositories.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public CityDTO insert(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        city = cityRepository.save(city);
        return new CityDTO(city);
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(Pageable pageable) {
        Page<City> cities = cityRepository.findAll(pageable);
        return cities.map(CityDTO::new).getContent();
    }

}
