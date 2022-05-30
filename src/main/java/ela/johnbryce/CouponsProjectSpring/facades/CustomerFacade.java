package ela.johnbryce.CouponsProjectSpring.facades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ela.johnbryce.CouponsProjectSpring.beans.CategoryType;
import ela.johnbryce.CouponsProjectSpring.beans.Coupon;
import ela.johnbryce.CouponsProjectSpring.beans.Customer;
import ela.johnbryce.CouponsProjectSpring.exceptions.AlreadyPurchasedException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CouponExpiredException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CouponIsOutOfStockException;
import ela.johnbryce.CouponsProjectSpring.exceptions.IncorrectEmailOrPasswordException;

@Service
@Scope("prototype")
public class CustomerFacade extends ClientFacade {

	private int customerID;

	@Override
	public boolean login(String email, String password) throws IncorrectEmailOrPasswordException {
		Customer c = customerRepo.findCustomerByEmailAndPassword(email, password);
		if (c != null) {
			// saving the customer id
			customerID = c.getId();
			return true;
		} else {
			throw new IncorrectEmailOrPasswordException();
		}
	}

	public void buyCoupon(Coupon coupon)
			throws AlreadyPurchasedException, CouponIsOutOfStockException, CouponExpiredException {
		// runs over customer's coupons and check if the coupon already bought
		for (Coupon coup : customerRepo.findById(customerID).get().getCoupons()) {
			if (coup.getId() == coupon.getId()) {
				throw new AlreadyPurchasedException();
			}
		}
		// checking if there is coupon on stock
		if (coupon.getAmount() <= 0) {
			throw new CouponIsOutOfStockException();
		}
		// checking if the coupon has not expired
		if (coupon.getEndDate().before(new Date())) {
			throw new CouponExpiredException();
		}
		Customer c = customerRepo.findById(customerID).orElseThrow();
		// add the coupon to customer's coupons
		c.getCoupons().add(coupon);
		// update customer
		customerRepo.save(c);
		// change the amount
		coupon.setAmount(coupon.getAmount() - 1);
		// update coupon
		couponRepo.save(coupon);

	}

	public Coupon getOneCoupon(int id) {
		return couponRepo.findById(id).orElseThrow();
	}
	
	public List<Coupon> getAllCoupons() {
		return couponRepo.findAll();
	}

	public Set<Coupon> getCustomerCoupons() {
		Customer cust = customerRepo.findById(customerID).orElseThrow();
		return cust.getCoupons();
	}

	public List<Coupon> getCustomerCouponsByCategory(CategoryType category) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		// runs over customer's coupons
		for (Coupon coup : getCustomerCoupons()) {
			if (coup.getCategory() == category) {
				// add coupon to list if it's match the category
				coupons.add(coup);
			}
		}
		return coupons;
	}

	public List<Coupon> getCustomerCouponsByMaxPrice(double price) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		// runs over customer's coupons
		for (Coupon coup : getCustomerCoupons()) {
			// add coupon to list if it's smaller then price
			if (coup.getPrice() < price) {
				coupons.add(coup);
			}
		}
		return coupons;
	}

	public Customer getCustomerDetails() {
		return customerRepo.findById(customerID).orElseThrow();
	}
}
