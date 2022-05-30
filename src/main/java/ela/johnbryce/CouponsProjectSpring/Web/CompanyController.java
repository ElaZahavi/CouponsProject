package ela.johnbryce.CouponsProjectSpring.Web;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ela.johnbryce.CouponsProjectSpring.beans.CategoryType;
import ela.johnbryce.CouponsProjectSpring.beans.Company;
import ela.johnbryce.CouponsProjectSpring.beans.Coupon;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotDeleteCouponException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotUpdateCouponException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CouponExistsException;
import ela.johnbryce.CouponsProjectSpring.exceptions.IllegalDatesException;
import ela.johnbryce.CouponsProjectSpring.exceptions.MissingDetailsException;
import ela.johnbryce.CouponsProjectSpring.facades.CompanyFacade;

@RestController
@RequestMapping("company")
//@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	@Autowired
	Map<String, OurSession> sessionsMap;

	@PostMapping("/coupon/{token}")
	public ResponseEntity<String> addCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
		try {
			CompanyFacade comp = (CompanyFacade) sessionsMap.get(token).getFacade();
			comp.addCoupon(coupon);
			return ResponseEntity.ok("Coupon added!");
		} catch (CouponExistsException | MissingDetailsException | IllegalDatesException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/updateCoupon/{token}")
	public ResponseEntity<String> updateCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
		try {
			CompanyFacade comp = (CompanyFacade) sessionsMap.get(token).getFacade();
			comp.updateCoupon(coupon);
			return ResponseEntity.ok("Coupon updated!");
		} catch (CannotUpdateCouponException | CouponExistsException | IllegalDatesException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/deleteCoupon/{token}/{id}")
	public ResponseEntity<String> deleteCoupon(@PathVariable String token, @PathVariable int id) {
		try {
			CompanyFacade comp = (CompanyFacade) sessionsMap.get(token).getFacade();
			comp.deleteCoupon(id);
			return ResponseEntity.ok("Coupon deleted");
		} catch (CannotDeleteCouponException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/getCompanyCoupons/{token}")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable String token) {
		CompanyFacade comp = (CompanyFacade)sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(comp.getCompanyCoupons());

	}
	
	@GetMapping("/getOneCompanyCoupon/{token}/{id}")
	public ResponseEntity<Coupon> getOneCompanyCoupon(@PathVariable String token, @PathVariable int id) {
		CompanyFacade comp = (CompanyFacade)sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(comp.getOneCompanyCoupon(id));

	}

	@GetMapping("/byCategory/{token}/{category}")
	public ResponseEntity<List<Coupon>> getCompanyCouponsByCategory(@PathVariable String token, @PathVariable CategoryType category) {
		CompanyFacade comp = (CompanyFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(comp.getCompanyCouponsByCategory(category));
	}

	@GetMapping("/byMaxPrice/{token}/{maxPrice}")
	public ResponseEntity<List<Coupon>> getCompanyCouponsByMaxPrice(@PathVariable String token, @PathVariable int maxPrice) {
		CompanyFacade comp = (CompanyFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(comp.getCompanyCouponsByMaxPrice(maxPrice));
	}
	
	@GetMapping("/companyDetails/{token}")
	public ResponseEntity<Company> getCompanyDetails(@PathVariable String token) {
		CompanyFacade comp = (CompanyFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(comp.getCompanyDetails());
	}
}