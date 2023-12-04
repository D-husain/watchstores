package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.AdminDao;
import com.example.demo.dto.SliderDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Slider;
import com.example.demo.repository.SliderRepository;
import com.example.demo.uplod.Upload_File;

import jakarta.servlet.http.HttpSession;

@Controller
public class SliderController {

	@Autowired
	private AdminDao adao;
	@Autowired
	private Upload_File fileuploadhelper;
	@Autowired
	private SliderRepository sliderRepository;
	String uploadSlider = "src/main/resources/static/images/slider";
	
	@GetMapping("/sliders")
	public String slider(Model model) {
		model.addAttribute("slider", adao.ShowSlider());
		model.addAttribute("category", adao.ShowCategory());
		return "admin/sliders";
	}

	@GetMapping("/addslider")
	public String addslider(Model model) {
		model.addAttribute("category", adao.ShowCategory());
		return "admin/addslider";
	}

	@PostMapping("slideradd")
	public String slideradd(@RequestParam("image") MultipartFile image, @RequestParam("name") String name,
			@RequestParam("tital") String tital, @RequestParam("cname") String category, Model model,
			HttpSession session) {
		Category c=adao.getCategoryByName(category);
		Slider slider = new Slider();
		slider.setName(name);
		slider.setTital(tital);
		slider.setCategory(c);
		slider.setImage(image.getOriginalFilename());

		try {
			if (!image.isEmpty()) {
				boolean uploadfile = fileuploadhelper.uploadFile(image, uploadSlider);
				if (uploadfile) {
					session.setAttribute("message", "data successfully Inserted");
				}
			} else {
				session.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean s = adao.AddSlider(slider);
		if (s) {
			session.setAttribute("message", "data successfully Inserted...");
		} else {
			session.setAttribute("message", "data is not inserted");
		}

		return "redirect:/sliders";
	}
	
	
	//---------------------------------------------- API ---------------------------------------------------------------
	
	@GetMapping("/slider/data")
	public ResponseEntity<List<SliderDTO>> getSliderList() {
		List<Slider> slider = adao.ShowSlider();
		List<SliderDTO> slidersDTO = new ArrayList<>();

		for (Slider sliders : slider) {
			SliderDTO sdto = new SliderDTO();
			sdto.setId(sliders.getId());
			sdto.setName(sliders.getName());
			sdto.setTital(sliders.getTital());
			sdto.setImage(sliders.getImage());
			sdto.setCategory(sliders.getCategory().getCname());
			slidersDTO.add(sdto);
		}

		return new ResponseEntity<>(slidersDTO, HttpStatus.OK);
	}
	
	@PostMapping("/admin/slider")
	public ResponseEntity<Map<String, String>> addSlider(@RequestParam("image") MultipartFile image,
			@RequestParam("name") String name, @RequestParam("tital") String tital,
			@RequestParam("cname") String category) {

		Map<String, String> response = new HashMap<>();
		Category c = adao.getCategoryByName(category);

		if (c == null) {
			response.put("message", "Category not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		Slider slider = new Slider();
		slider.setName(name);
		slider.setTital(tital);
		slider.setCategory(c);
		slider.setImage(image.getOriginalFilename());

		try {
			if (!image.isEmpty()) {
				boolean uploadFile = fileuploadhelper.uploadFile(image, uploadSlider);
				if (uploadFile) {
					response.put("message", "Data successfully inserted");
				}
			} else {
				response.put("message", "File is empty");
			}
		} catch (Exception e) {
			response.put("message", "An error occurred while uploading the file");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		boolean success = adao.AddSlider(slider);
		if (success) {
			response.put("message", "Data successfully inserted");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("message", "Data is not inserted");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("slider/{id}")
    public ResponseEntity<SliderDTO> getSliderById(@PathVariable Integer id) {
		SliderDTO sliderDTO = adao.getapiSliderById(id);
        return ResponseEntity.ok(sliderDTO);
    }
	
	@PutMapping("/slider/update/{id}")
	public ResponseEntity<String> updateCategory(@RequestParam("image") MultipartFile image,
			@RequestParam("slidername") String name, @RequestParam("tital") String tital,
			@RequestParam("category") String category, @PathVariable("id") Integer id) {
		Optional<Slider> existingSlider = sliderRepository.findById(id);

		Category c = adao.getCategoryByName(category);

		 if (existingSlider.isPresent()) {
		        try {
		            Slider foundSlider = existingSlider.get();
		            foundSlider.setName(name);
		            foundSlider.setTital(tital);
		            foundSlider.setCategory(c);
					
		            handleFileUpload(image, foundSlider);

		            sliderRepository.save(foundSlider);
		            return ResponseEntity.ok("Slider updated successfully");
		        } catch (Exception e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body("Failed to update slider: " + e.getMessage());
		        }
		    } else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("slider not found");
		    }
		}
	
	private void handleFileUpload(MultipartFile image, Slider foundSlider) throws FileUploadException {
	    if (image != null && !image.isEmpty()) {
	        try {
	            boolean uploadSuccessful = fileuploadhelper.uploadFile(image, uploadSlider);
	            if (uploadSuccessful) {
	            	foundSlider.setImage(image.getOriginalFilename());
	            } else {
	                throw new FileUploadException("Failed to upload one or more images");
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("Error uploading file: " + e.getMessage());
	        }
	    }
	}
	
	@DeleteMapping("/slider/delete/{id}")
	public ResponseEntity<Void> deleteSlider(@PathVariable Integer id) {
		adao.DeleteSlider(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
