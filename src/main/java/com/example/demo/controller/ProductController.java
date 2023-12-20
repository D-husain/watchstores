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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.AdminDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Country;
import com.example.demo.entity.Coupons;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductBrand;
import com.example.demo.entity.ProductCountryOrigin;
import com.example.demo.entity.Reviews;
import com.example.demo.entity.User;
import com.example.demo.repository.OriginRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.uplod.Upload_File;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	@Autowired private ProductRepository productrepo;
	@Autowired private AdminDao adao;
	@Autowired private Upload_File fileuploadhelper;
	@Autowired private UserDao udao;
	@Autowired private OriginRepository originRepository;
	String uploadProduct = "src/main/resources/static/images/product";
	
	
	@GetMapping("/products")
	public String products(Model model) {
		model.addAttribute("product", adao.ShowProduct());
		model.addAttribute("category", adao.ShowCategory());
		model.addAttribute("countryorigin", adao.ShowCountryOrigin());
		model.addAttribute("brand", adao.ShowBrand());
		return "admin/products";
	}

	@GetMapping("/addproduct")
	public String addproduct(Model model) {
		model.addAttribute("category", adao.ShowCategory());
		model.addAttribute("country", adao.ShowCountry());
		model.addAttribute("brand", adao.ShowBrand());
		return "admin/addproduct";
	}
	
	@PostMapping("/productadd")
	public String productadd(@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2,
			@RequestParam("img3") MultipartFile img3, @RequestParam("img4") MultipartFile img4,
			@RequestParam("pname") String pname, @RequestParam("category") String category,@RequestParam("colore") String colore,
			@RequestParam("brand") String brand, @RequestParam("description") String description,
			@RequestParam("specification") String specification, @RequestParam("gname") String gname,
			@RequestParam("country") String country, @RequestParam("qty") int qty, @RequestParam("price") Double price,
			@RequestParam("availability") String Availability, HttpSession hs) {

		ProductBrand b = adao.getbrandByName(brand);
		Category c = adao.getCategoryByName(category);
		ProductCountryOrigin coo = adao.getCountryOriginByName(country);

		Product product = new Product();
		product.setPname(pname);
		product.setCategory(c);
		product.setBrand(b);
		product.setDescription(description);
		product.setSpecification(specification);
		product.setColore(colore);
		product.setGenericname(gname);
		product.setCountry(coo);
		product.setQty(qty);
		product.setPrice(price);
		product.setAvailability(Availability);
		product.setImg1(img1.getOriginalFilename());
		product.setImg2(img2.getOriginalFilename());
		product.setImg3(img3.getOriginalFilename());
		product.setImg4(img4.getOriginalFilename());

		try {
			if (!img1.isEmpty()) {
				boolean uploadfile = fileuploadhelper.uploadFile(img1, uploadProduct);
				fileuploadhelper.uploadFile(img2, uploadProduct);
				fileuploadhelper.uploadFile(img3, uploadProduct);
				fileuploadhelper.uploadFile(img4, uploadProduct);
				if (uploadfile) {
					hs.setAttribute("message", "data successfully Inserted");
				}
			} else {
				hs.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean p = adao.AddProduct(product);
		if (p) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		return "redirect:/products";
	}
	
	@GetMapping("editproduct")
	public String editProduct(@RequestParam("id") Integer id, Model model) {
		Product product=adao.getProductById(id);
		model.addAttribute("product", product);
		
		model.addAttribute("category", adao.ShowCategory());
		model.addAttribute("country", adao.ShowCountry());
		model.addAttribute("brand", adao.ShowBrand());
		return "admin/editproduct";
	}
	
	@PostMapping("/updateproduct")
	public String productUpdate(@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2,
			@RequestParam("img3") MultipartFile img3, @RequestParam("img4") MultipartFile img4,
			@RequestParam("pname") String pname, @RequestParam("category") String category,@RequestParam("colore") String colore,
			@RequestParam("brand") String brand, @RequestParam("description") String description,
			@RequestParam("specification") String specification, @RequestParam("gname") String gname,
			@RequestParam("country") String country, @RequestParam("qty") int qty, @RequestParam("price") Double price,
			@RequestParam("availability") String Availability,@RequestParam("id") Integer id, HttpSession hs) {

		ProductBrand b = adao.getbrandByName(brand);
		Category c = adao.getCategoryByName(category);
		ProductCountryOrigin coo = adao.getCountryOriginByName(country);

		Product product = new Product();
		product.setId(id);
		product.setPname(pname);
		product.setCategory(c);
		product.setBrand(b);
		product.setColore(colore);
		product.setDescription(description);
		product.setSpecification(specification);
		product.setGenericname(gname);
		product.setCountry(coo);
		product.setQty(qty);
		product.setPrice(price);
		product.setAvailability(Availability);
		product.setImg1(img1.getOriginalFilename());
		product.setImg2(img2.getOriginalFilename());
		product.setImg3(img3.getOriginalFilename());
		product.setImg4(img4.getOriginalFilename());

		try {
			if (!img1.isEmpty()) {
				boolean uploadfile = fileuploadhelper.uploadFile(img1, uploadProduct);
				fileuploadhelper.uploadFile(img2, uploadProduct);
				fileuploadhelper.uploadFile(img3, uploadProduct);
				fileuploadhelper.uploadFile(img4, uploadProduct);
				if (uploadfile) {
					hs.setAttribute("message", "data successfully Inserted");
				}
			} else {
				hs.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean p = adao.AddProduct(product);
		if (p) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		return "redirect:/products";
	}
	
	@PostMapping("/addreview")
	public String addreview(@RequestParam("review") String review, @RequestParam("rating") Integer rating,
			@RequestParam("reviewdate") String reviewdate, @RequestParam("reviewtital") String reviewtital,
			@RequestParam("productId") Integer productId, HttpSession session, RedirectAttributes redAttributes) {
		User user = (User) session.getAttribute("user");
		Product p = adao.getProductById(productId);
		if (user == null) {
			return "redirect:/logins";
		} else {
			Reviews reviews = new Reviews();
			reviews.setReview(reviewtital);
			reviews.setRating(rating);
			reviews.setReviewdate(reviewdate);
			reviews.setReviewtital(reviewtital);
			reviews.setProduct(p);
			reviews.setUser(user);
			udao.AddRevirew(reviews);
		}
		return "redirect:/ship";
	}
	
	@ModelAttribute("review")
	public List<Reviews> getreview(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			List<Reviews> reviewlist = udao.viewUserreview(user);
			return reviewlist;
		} else {
			return new ArrayList<Reviews>();
		}
	}
	
	//-------------------------------------- API --------------------------------------------------------------------
	
	@GetMapping("/product/data")
	public ResponseEntity<List<ProductDTO>> getProductList() {
		  List<Product> products = adao.ShowProduct();
		  List<ProductDTO> Product = new ArrayList<>();
		  
		  for (Product product : products) {
			  ProductDTO productDTO = new ProductDTO();
		        productDTO.setId(product.getId());
		        productDTO.setPname(product.getPname());
		        productDTO.setAvailability(product.getAvailability());
		        productDTO.setCategory(product.getCategory().getCname());
		        Product.add(productDTO);
		    }
		  
		  return new ResponseEntity<>(Product, HttpStatus.OK);
	}
	
	@PostMapping("admin/productadd")
    public ResponseEntity<Map<String, String>> productADD(@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2,
			@RequestParam("img3") MultipartFile img3, @RequestParam("img4") MultipartFile img4,
			@RequestParam("pname") String pname, @RequestParam("category") String category,@RequestParam("colore") String colore,
			@RequestParam("brand") String brand, @RequestParam("description") String description,
			@RequestParam("specification") String specification, @RequestParam("gname") String gname,
			@RequestParam("country") String country, @RequestParam("qty") int qty, @RequestParam("price") Double price,
			@RequestParam("availability") String Availability,@RequestParam("stock") Integer stock) {
        Map<String, String> response = new HashMap<>();

        ProductBrand b = adao.getbrandByName(brand);
		Category c = adao.getCategoryByName(category);
		ProductCountryOrigin coo = adao.getCountryOriginByName(country);

		Product product = new Product();
		product.setPname(pname);
		product.setCategory(c);
		product.setBrand(b);
		product.setDescription(description);
		product.setSpecification(specification);
		product.setColore(colore);
		product.setGenericname(gname);
		product.setCountry(coo);
		product.setQty(qty);
		product.setPrice(price);
		product.setAvailability(Availability);
		product.setStock(stock);
		product.setImg1(img1.getOriginalFilename());
		product.setImg2(img2.getOriginalFilename());
		product.setImg3(img3.getOriginalFilename());
		product.setImg4(img4.getOriginalFilename());

        try {
        	if (!img1.isEmpty()) {
        		boolean uploadfile = fileuploadhelper.uploadFile(img1, uploadProduct);
				fileuploadhelper.uploadFile(img2, uploadProduct);
				fileuploadhelper.uploadFile(img3, uploadProduct);
				fileuploadhelper.uploadFile(img4, uploadProduct);
                if (uploadfile) {
                    response.put("message", "Image successfully inserted");
                }
            } else {
                response.put("message", "File is empty");
            }
        } catch (Exception e) {
            response.put("message", "An error occurred while uploading the file");
            e.printStackTrace();
        }

        boolean success = adao.AddProduct(product);
        if (success) {
            response.put("message", "Product successfully inserted");
        } else {
            response.put("message", "Data is not inserted");
        }
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        ProductDTO productDTO = adao.getapiProductById(id);
        return ResponseEntity.ok(productDTO);
    }
	
	@PutMapping("/product/update/{id}")
	public ResponseEntity<String> updateCategory(@RequestParam("img1") MultipartFile img1,
			@RequestParam("img2") MultipartFile img2, @RequestParam("img3") MultipartFile img3,
			@RequestParam("img4") MultipartFile img4, @RequestParam("pname") String pname,
			@RequestParam("category") String category, @RequestParam("colore") String colore,
			@RequestParam("brand") String brand, @RequestParam("description") String description,
			@RequestParam("specification") String specification, @RequestParam("gname") String gname,
			@RequestParam("country") String country, @RequestParam("qty") int qty, @RequestParam("price") Double price,
			@RequestParam("availability") String Availability, @PathVariable("id") Integer id,@RequestParam("stock") int stock) {
		Optional<Product> existingProduct = productrepo.findById(id);

		ProductBrand b = adao.getbrandByName(brand);
		Category c = adao.getCategoryByName(category);
		ProductCountryOrigin coo = adao.getCountryOriginByName(country);

		 if (existingProduct.isPresent()) {
		        try {
		            Product foundProduct = existingProduct.get();
		            foundProduct.setPname(pname);
					foundProduct.setCategory(c);
					foundProduct.setBrand(b);
					foundProduct.setDescription(description);
					foundProduct.setSpecification(specification);
					foundProduct.setColore(colore);
					foundProduct.setGenericname(gname);
					foundProduct.setCountry(coo);
					foundProduct.setQty(qty);
					foundProduct.setPrice(price);
					foundProduct.setAvailability(Availability);
					foundProduct.setStock(stock);
					
		            handleFileUpload(img1, img2, img3, img4, foundProduct);

		            productrepo.save(foundProduct);
		            return ResponseEntity.ok("Product updated successfully");
		        } catch (Exception e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body("Failed to update product: " + e.getMessage());
		        }
		    } else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		    }
		}
	
	private void handleFileUpload(MultipartFile img1, MultipartFile img2, MultipartFile img3, MultipartFile img4, Product foundProduct) throws FileUploadException {
	    if (img1 != null && !img1.isEmpty()) {
	        try {
	            boolean uploadSuccessful = fileuploadhelper.uploadFile(img1, uploadProduct);
	            if (uploadSuccessful) {
	                foundProduct.setImg1(img1.getOriginalFilename());
	                foundProduct.setImg2(img2.getOriginalFilename()); 
	                foundProduct.setImg3(img3.getOriginalFilename());
	                foundProduct.setImg4(img4.getOriginalFilename());
	            } else {
	                throw new FileUploadException("Failed to upload one or more images");
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("Error uploading file: " + e.getMessage());
	        }
	    }
	}
	
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<Void> deleteproduct(@PathVariable Integer id) {
		adao.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	//Product Country Origin
	
	@PostMapping("/product/origin/save")
	public ResponseEntity<Void> saveproductOrigin(@RequestBody ProductCountryOrigin countryOrigin) {
		adao.AddOrigin(countryOrigin);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@GetMapping("/product/countryorigin")
	public ResponseEntity<List<ProductCountryOrigin>> getAllCountriesOrigin() {
		List<ProductCountryOrigin> origin = adao.ShowCountryOrigin();
		if (origin != null && !origin.isEmpty()) {
			return new ResponseEntity<>(origin, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("product/countryorigin/{id}")
    public ResponseEntity<ProductCountryOrigin> getProductOriginById(@PathVariable Integer id) {
		ProductCountryOrigin productOrigin = adao.getCountryOriginById(id);
        return ResponseEntity.ok(productOrigin);
    }
	
	@PutMapping("/product/origin/update/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable("id") Integer id, @RequestBody ProductCountryOrigin countryOrigin) {
		Optional<ProductCountryOrigin> existingOrigin = originRepository.findById(id);

		if (existingOrigin.isPresent()) {
			ProductCountryOrigin foundProduct = existingOrigin.get();
			foundProduct.setCountryname(countryOrigin.getCountryname());

			originRepository.save(foundProduct);

			return ResponseEntity.ok("Country updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country not found");
		}
	}
	
	@DeleteMapping("/product/origin/delete/{id}")
	public ResponseEntity<Void> deletecountryorigin(@PathVariable Integer id) {
		adao.DeleteCountryorigin(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
