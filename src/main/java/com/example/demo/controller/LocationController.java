package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.AdminDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.City;
import com.example.demo.entity.Country;
import com.example.demo.entity.States;
import com.example.demo.uplod.Upload_File;

import jakarta.servlet.http.HttpSession;

@Controller
public class LocationController {

    @Autowired
    private AdminDao adao;
    @Autowired
    private UserDao udao;
    @Autowired
	private Upload_File fileuploadhelper;
    String uploadLocation = "src/main/resources/static/images/location";

    
    @GetMapping("/country")
	public String country(Model model) {
		return "admin/country";
	}

	@GetMapping("/addcountry")
	public String addcountry() {
		return "admin/addcountry";
	}

	@PostMapping("/countryadd")
	public String CountryAdd(@RequestParam("img") MultipartFile cimg, @RequestParam("cname") String cname,
			HttpSession hs) {

		Country country = new Country();
		country.setCountryname(cname);
		country.setImg(cimg.getOriginalFilename());

		try {
			if (!cimg.isEmpty()) {
				boolean uploadfile = fileuploadhelper.uploadFile(cimg, uploadLocation);
				if (uploadfile) {
					hs.setAttribute("message", "data successfully Inserted");
				}
			} else {
				hs.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean c = adao.Addcountry(country);
		if (c) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		return "redirect:/country";
	}

	
	//----------------------------------------- API -------------------------------------------------------------------
    
	@GetMapping("/api/country")
	public ResponseEntity<List<Country>> getAllCountries() {
		List<Country> countries = adao.ShowCountry();
		if (countries != null && !countries.isEmpty()) {
			return new ResponseEntity<>(countries, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/api/state")
	public ResponseEntity<List<States>> getAllState() {
		try {
			List<States> states = udao.ShowState();
			if (states != null && !states.isEmpty()) {
				return new ResponseEntity<>(states, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/city")
	public ResponseEntity<List<City>> getAllCity() {
		try {
			List<City> cities = udao.showCity();
			if (cities != null && !cities.isEmpty()) {
				return new ResponseEntity<>(cities, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    
    @DeleteMapping("/country/delete/{id}")
	public ResponseEntity<Void> deletecountry(@PathVariable Integer id) {
		adao.DeleteCountry(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
