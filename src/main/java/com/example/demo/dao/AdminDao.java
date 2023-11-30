package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SliderDTO;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Category;
import com.example.demo.entity.Country;
import com.example.demo.entity.Coupons;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductBrand;
import com.example.demo.entity.ProductCountryOrigin;
import com.example.demo.entity.Slider;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.CouponRepository;
import com.example.demo.repository.OriginRepository;
import com.example.demo.repository.ProductBrandRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SliderRepository;

@Service
public class AdminDao {
	
	@Autowired
	private AdminRepository adminrepo;
	@Autowired
	private SliderRepository sliderrepo;
	@Autowired
	private ProductBrandRepository brandrepo;
	@Autowired
	private CategoryRepository categoryrepo;
	@Autowired
	private CountryRepository countryrepo;
	@Autowired 
	private ProductRepository productrepo;
	@Autowired 
	private CouponRepository couponrepo;
	@Autowired 
	private OriginRepository originRepository;
	//-----------------------------------------------Admit--------------------------------------------------------------
	
	public List<Admin> fechAllUser() {
		return this.adminrepo.findAll();
	}
	
	//-----------------------------------------------Brand--------------------------------------------------------------
	
	public boolean AddBrand(ProductBrand brand) {
		return this.brandrepo.save(brand)!= null;
	}
	
	public List<ProductBrand> ShowBrand(){
        return brandrepo.findAll();
    }
	
	public ProductBrand getbrandByName(String brand) {
        return brandrepo.findBybrandname(brand)
            .orElse(null); 
    }
	
	//-----------------------------------------------Category--------------------------------------------------------------
	
	public boolean AddCategory(Category category) {
		return this.categoryrepo.save(category)!= null;
	}
	
	public List<Category> ShowCategory(){
        return categoryrepo.findAll();
    }
	
	public Category getCategoryById(int id) {
		Optional<Category> category = categoryrepo.findById(id);
		if (category.isPresent()) {
			return category.get();
		}
		return null;
	}
	
	public Category getCategoryByIds(Integer id) {
		return categoryrepo.findById(id).get();
	}
	
	public Category getCategoryByName(String categoryName) {
        return categoryrepo.findByCname(categoryName)
            .orElse(null); 
    }
	
	public void DeleteCategory(int id) {
		categoryrepo.deleteById(id);
	}
	
	//-----------------------------------------------Country-------------------------------------------------------------
	
	public boolean Addcountry(Country country) {
		return this.countryrepo.save(country)!=null;
	}
	
	public List<Country> ShowCountry(){
        return countryrepo.findAll();
    }
	
	public Country getCountryByName(String countryname) {
		 return countryrepo.findByCountryName(countryname)
		            .orElse(null);
	}
	
	public Country getCountryById(int id) {
		Optional<Country> country = countryrepo.findById(id);
		if (country.isPresent()) {
			return country.get();
		}
		return null;
	}
	
	public void DeleteCountry(int id) {
		countryrepo.deleteById(id);
	}
	
	//-----------------------------------------------Product-------------------------------------------------------------
	
	public boolean AddProduct(Product product) {
		return this.productrepo.save(product)!=null;
	}
	
	public List<Product> ShowProduct(){
        return productrepo.findAll(sortByIdAsc());
    }
	
	private Sort sortByIdAsc() {
        return Sort.by(Sort.Direction.DESC, "id");
    }
	
	public Product getProductById(int id) {
		Optional<Product> product = productrepo.findById(id);
		if (product.isPresent()) {
			return product.get();
		}
		return null;
	}
	
	public ProductDTO getapiProductById(int id) {
        Product product = null;
		try {
			product = productrepo.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

        return mapProductToDTO(product);
    }
	

    private ProductDTO mapProductToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setPname(product.getPname());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory().getCname());
        productDTO.setImg1(product.getImg1());
        productDTO.setImg2(product.getImg2());
        productDTO.setImg3(product.getImg3());
        productDTO.setImg4(product.getImg4());
        productDTO.setBrand(product.getBrand().getBrand());
        productDTO.setColore(product.getColore());
        productDTO.setDescription(product.getDescription());
        productDTO.setSpecification(product.getSpecification());
        productDTO.setGenericname(product.getGenericname());
        productDTO.setCountry(product.getCountry().getCountryname());
        productDTO.setQty(product.getQty());
        productDTO.setAvailability(product.getAvailability());
        return productDTO;
    }
	
	public void deleteProduct(Integer id) {
		productrepo.deleteById(id);
	}

	public List<Product> viewProductsByCategoryId(int cid) {
		return productrepo.findByProductCategoryId(cid);
	}
	
	public List<Product> viewProductsByCategoryName(String category) {
		return productrepo.findByProductCategoryName(category);
	}
	
	
	public List<Product> viewProductsByBrandId(int bid) {
		return productrepo.findByProductBrandId(bid);
	}
	
	public List<Product> viewProductsByBrandName(String brand) {
		return productrepo.findByProductBrandName(brand);
	}
	
	public List<Product> sortByPriceLowToHigh() {
        return productrepo.sortByPriceLowToHigh();
    }
	
	public List<Product> sortByPriceHighToLow() {
        return productrepo.sortByPriceHighToLow();
    }
	
	public List<Product> sortByProductNameZ() {
        return productrepo.sortByProductNameZ();
    }
	

	public List<Product> sortByProductNameA() {
        return productrepo.sortByProductNameA();
    }

    public int getTotalPages(int pageSize) {
        int totalProducts = productrepo.countAllProducts();
        return (int) Math.ceil((double) totalProducts / pageSize);
    }
    
    //----------------------------------------Product origin country --------------------------------------------------
    
    public boolean AddOrigin(ProductCountryOrigin countryOrigin) {
		return this.originRepository.save(countryOrigin)!=null;
	}
	
	public List<ProductCountryOrigin> ShowCountryOrigin(){
        return originRepository.findAll();
    }
	
	public ProductCountryOrigin getCountryOriginByName(String CountryOriginByName) {
		 return originRepository.findByCountryOriginName(CountryOriginByName)
		            .orElse(null);
	}
	
	public ProductCountryOrigin getCountryOriginById(int id) {
		Optional<ProductCountryOrigin> origin = originRepository.findById(id);
		if (origin.isPresent()) {
			return origin.get();
		}
		return null;
	}
	
	public void DeleteCountryorigin(int id) {
		originRepository.deleteById(id);
	}
    
	
	//-----------------------------------------------coupon--------------------------------------------------------------
	
	public void AddCoupon(Coupons coupons) {
		couponrepo.save(coupons);
	}
	
	public List<Coupons> ShowCoupon() {
		return this.couponrepo.findAll();
	}
	
	public void DeleteCoupon(int id) {
		couponrepo.deleteById(id);
	}
	
	//-----------------------------------------------slider--------------------------------------------------------------
	
	public boolean AddSlider(Slider slider) {
		return this.sliderrepo.save(slider)!=null;
	}
	
	public List<Slider>ShowSlider(){
		return this.sliderrepo.findAll();
	}
	
	public SliderDTO getapiSliderById(int id) {
        Slider slider = null;
		try {
			slider = sliderrepo.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

        return mapSliderToDTO(slider);
    }

	private SliderDTO mapSliderToDTO(Slider slider) {
		SliderDTO sliderDTO =new SliderDTO();
		sliderDTO.setId(slider.getId());
		sliderDTO.setName(slider.getName());
		sliderDTO.setTital(slider.getTital());
		sliderDTO.setCategory(slider.getCategory().getCname());
		sliderDTO.setImage(slider.getImage());
		return sliderDTO;
	}
	
	public void DeleteSlider(Integer id) {
		sliderrepo.deleteById(id);
		
	}

	

}
