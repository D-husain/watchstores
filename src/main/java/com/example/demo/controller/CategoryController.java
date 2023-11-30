package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.AdminDao;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.uplod.Upload_File;

import jakarta.servlet.http.HttpSession;

@Controller
public class CategoryController {

	@Autowired
	private AdminDao adao;
	@Autowired
	private Upload_File fileuploadhelper;
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/category")
	public String category(Model model) {
		model.addAttribute("category", adao.ShowCategory());
		return "admin/category";
	}

	@GetMapping("/addcategory")
	public String addcategory() {
		return "admin/addcategory";
	}

	@PostMapping("/categoryadd")
	public String categoryadd(@RequestParam("cimg") MultipartFile cimg, @RequestParam("cname") String cname,
			HttpSession hs) {

		Category category = new Category();
		category.setCname(cname);
		category.setCimg(cimg.getOriginalFilename());

		try {
			if (!cimg.isEmpty()) {
				boolean uploadfile = fileuploadhelper.uploadFile(cimg);
				if (uploadfile) {
					hs.setAttribute("message", "data successfully Inserted");
				}
			} else {
				hs.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean s = adao.AddCategory(category);
		if (s) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		return "redirect:/category";
	}

	@GetMapping("/editcategory")
	public String edit(@RequestParam("id") Integer id, Model m) {
		Category category = adao.getCategoryById(id);
		m.addAttribute("category", category);
		return "admin/editcategory";
	}

	@PostMapping("/updatecategory")
	public String updatecategory(@RequestParam("cimg") MultipartFile cimg, @RequestParam("cname") String cname,
			@RequestParam("id") int cid, HttpSession hs) {

		Category category = new Category();
		category.setId(cid);
		category.setCname(cname);
		category.setCimg(cimg.getOriginalFilename());

		try {
			if (!cimg.isEmpty()) {
				boolean uploadfile = fileuploadhelper.uploadFile(cimg);
				if (uploadfile) {
					hs.setAttribute("message", "data successfully Inserted");
				}
			} else {
				hs.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean s = adao.AddCategory(category);
		if (s) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		List<Category> cat = adao.ShowCategory();

		hs.setAttribute("category", cat);
		return "redirect:/category";
	}

	@GetMapping("/deletecategory/{id}")
	public String deleteser(@PathVariable int id, HttpSession session) {
		adao.DeleteCategory(id);
		session.setAttribute("ser", "Services Delete Sucessfully..");
		return "redirect:/category";
	}

	// ----------------------------------------- API -------------------------------------------------------------------

	@GetMapping("/category/data")
	public ResponseEntity<List<Category>> getCategoryList() {
		return new ResponseEntity<List<Category>>(adao.ShowCategory(), HttpStatus.OK);
	}

	@PostMapping("admin/categoryadd")
	public ResponseEntity<Map<String, String>> categoryadd(@RequestParam("cimg") MultipartFile cimg,
			@RequestParam("cname") String cname) {
		Map<String, String> response = new HashMap<>();

		Category category = new Category();
		category.setCname(cname);
		category.setCimg(cimg.getOriginalFilename());

		try {
			if (!cimg.isEmpty()) {
				boolean uploadFile = fileuploadhelper.uploadFile(cimg);
				if (uploadFile) {
					response.put("message", "Image successfully inserted");
				}
			} else {
				response.put("message", "File is empty");
			}
		} catch (Exception e) {
			response.put("message", "An error occurred while uploading the file");
			e.printStackTrace();
		}

		boolean success = adao.AddCategory(category);
		if (success) {
			response.put("message", "Category successfully inserted");
		} else {
			response.put("message", "Data is not inserted");
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<Category> getcategory(@PathVariable Integer id) {
		return new ResponseEntity<Category>(adao.getCategoryByIds(id), HttpStatus.OK);
	}

	@PutMapping("/category/update/{id}")
	public ResponseEntity<String> updateCategory(@RequestParam(value = "cimg", required = false) MultipartFile cimg,
			@PathVariable("id") Integer id, @RequestParam("cname") String cname) {
		Optional<Category> existingCategory = categoryRepository.findById(id);

		if (existingCategory.isPresent()) {
			Category foundCategory = existingCategory.get();

			foundCategory.setCname(cname);

			if (cimg != null && !cimg.isEmpty()) {
				try {
					boolean uploadFile = fileuploadhelper.uploadFile(cimg);
					if (uploadFile) {
						foundCategory.setCimg(cimg.getOriginalFilename());
					} else {
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Failed to upload the image");
					}
				} catch (Exception e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("An error occurred while uploading the file: " + e.getMessage());
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
			}

			categoryRepository.save(foundCategory);

			return ResponseEntity.ok("Category updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
		}
	}

	@DeleteMapping("/category/delete/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
		adao.DeleteCategory(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
