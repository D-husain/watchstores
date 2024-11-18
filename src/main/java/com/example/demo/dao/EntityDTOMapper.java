package com.example.demo.dao;

import com.example.demo.dto.CityDTO;
import com.example.demo.dto.CountryDTO;
import com.example.demo.dto.StateDTO;
import com.example.demo.entity.City;
import com.example.demo.entity.Country;
import com.example.demo.entity.States;

public class EntityDTOMapper {

	 public static CountryDTO toCountryDTO(Country country) {
	        CountryDTO dto = new CountryDTO();
	        dto.setId(country.getId());
	        dto.setCountryname(country.getCountryname());
	        return dto;
	    }

	    public static StateDTO toStatesDTO(States state) {
	        StateDTO dto = new StateDTO();
	        dto.setId(state.getId());
	        dto.setName(state.getName());
	        dto.setCountryId(state.getCountry().getId());
	        return dto;
	    }

	    public static CityDTO toCityDTO(City city) {
	        CityDTO dto = new CityDTO();
	        dto.setId(city.getId());
	        dto.setCity(city.getCity());
	        dto.setStateId(city.getStates().getId());
	        return dto;
	    }
}
