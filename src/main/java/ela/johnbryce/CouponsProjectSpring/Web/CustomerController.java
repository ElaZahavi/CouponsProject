package ela.johnbryce.CouponsProjectSpring.Web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ela.johnbryce.CouponsProjectSpring.beans.CategoryType;
import ela.johnbryce.CouponsProjectSpring.beans.Coupon;
import ela.johnbryce.CouponsProjectSpring.beans.Customer;
import ela.johnbryce.CouponsProjectSpring.exceptions.AlreadyPurchasedException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CouponExpiredException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CouponIsOutOfStockException;
import ela.johnbryce.CouponsProjectSpring.facades.CustomerFacade;

@RestController
@RequestMapping("customer")
//@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	Map<String, OurSession> sessionsMap;

	@PostMapping("/buyCoupon/{token}")
	public ResponseEntity<String> buyCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
		try {
			CustomerFacade cust = (CustomerFacade) sessionsMap.get(token).getFacade();
			cust.buyCoupon(coupon);
			return ResponseEntity.ok("Purchase succeeded!");
		} catch (AlreadyPurchasedException | CouponIsOutOfStockException | CouponExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/getOneCoupon/{token}/{id}")
	public ResponseEntity<Coupon> getOneCoupon(@PathVariable String token, @PathVariable int id) {
		CustomerFacade cust = (CustomerFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(cust.getOneCoupon(id));
	}
	
	@GetMapping("/getAllCoupons/{token}")
	public ResponseEntity<List<Coupon>> getAllCoupons(@PathVariable String token) {
		CustomerFacade cust = (CustomerFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(cust.getAllCoupons());
	}

	@GetMapping("/customerCoupons/{token}")
	public ResponseEntity<Set<Coupon>> getCustomerCoupons(@PathVariable String token) {
		CustomerFacade cust = (CustomerFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(cust.getCustomerCoupons());
	}

	@GetMapping("/byCategory/{token}/{category}")
	public ResponseEntity<List<Coupon>> getCustomerCouponsByCategory(@PathVariable String token,
			@PathVariable CategoryType category) {
		CustomerFacade cust = (CustomerFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(cust.getCustomerCouponsByCategory(category));
	}

	@GetMapping("/byMaxPrice/{token}/{maxPrice}")
	public ResponseEntity<List<Coupon>> getCustomerCouponsByMaxPrice(@PathVariable String token, @PathVariable double maxPrice) {
		CustomerFacade cust = (CustomerFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(cust.getCustomerCouponsByMaxPrice(maxPrice));
	}

	@GetMapping("/customerDetails/{token}")
	public ResponseEntity<Customer> getCustomerDetails(@PathVariable String token) {
		CustomerFacade cust = (CustomerFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(cust.getCustomerDetails());
	}
}