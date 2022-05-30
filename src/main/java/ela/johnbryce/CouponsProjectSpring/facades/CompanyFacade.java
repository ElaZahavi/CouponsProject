package ela.johnbryce.CouponsProjectSpring.facades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ela.johnbryce.CouponsProjectSpring.beans.CategoryType;
import ela.johnbryce.CouponsProjectSpring.beans.Company;
import ela.johnbryce.CouponsProjectSpring.beans.Coupon;
import ela.johnbryce.CouponsProjectSpring.beans.Customer;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotDeleteCouponException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotUpdateCouponException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CouponExistsException;
import ela.johnbryce.CouponsProjectSpring.exceptions.IllegalDatesException;
import ela.johnbryce.CouponsProjectSpring.exceptions.IncorrectEmailOrPasswordException;
import ela.johnbryce.CouponsProjectSpring.exceptions.MissingDetailsException;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {

	private int companyID;

	@Override
	public boolean login(String email, String password) throws IncorrectEmailOrPasswordException {
		Company c = companyRepo.findCompanyByEmailAndPassword(email, password);
		if (c != null) {
			// saving the company id
			companyID = c.getId();
			return true;
		}
		throw new IncorrectEmailOrPasswordException();
	}
	
	public void addCoupon(Coupon coupon) throws CouponExistsException, MissingDetailsException, IllegalDatesException {
		Calendar cal = Calendar.getInstance();
		if(coupon.getAmount() == 0 || coupon.getCategory() == null || coupon.getDescription() == null  || coupon.getDescription().equals("")
				|| coupon.getEndDate() == null || coupon.getImage() == null || coupon.getImage().equals("") 
				|| coupon.getPrice() == 0 || coupon.getStartDate() == null || coupon.getTitle() == null || coupon.getTitle().equals("")) {
			throw new MissingDetailsException();
		} else if (couponRepo.findCouponByCompanyIdAndTitle(companyID, coupon.getTitle()) != null) {
			throw new CouponExistsException();
		} else if (coupon.getEndDate().before(coupon.getStartDate()) || coupon.getEndDate().before(cal.getTime())) {
			throw new IllegalDatesException();
		} else {
			coupon.setCompany(companyRepo.findById(companyID).orElseThrow());
			couponRepo.save(coupon);
		}
	}

	public void updateCoupon(Coupon coupon) throws CannotUpdateCouponException, CouponExistsException, IllegalDatesException {
		Calendar cal = Calendar.getInstance();
		boolean exist = false;
		if (coupon.getEndDate().before(coupon.getStartDate()) || coupon.getEndDate().before(cal.getTime())) {
			throw new IllegalDatesException();
		}
		// save the coupon from db in coupon2, throw exception if the id not exist in db
		// (cannot update company id but im using the "set company" method)
		Coupon coupon2 = couponRepo.findById(coupon.getId()).orElseThrow(CannotUpdateCouponException::new);
		// check if the company id in coupon and coupon2 are equals and coupon company id is equal to companyID(company that logged in)
		if (coupon.getCompany().getId() == coupon2.getCompany().getId() && coupon.getCompany().getId() == companyID) {
			// runs over all coupons and check if title already exist in company's coupons
			for (Coupon coup : couponRepo.findCouponByCompanyId(companyID)) {
				if (coup.getTitle().equals(coupon.getTitle()) && coup.getId() != coupon.getId()) {
					exist = true;
					break;
				}
			}
			if (exist == false) {
				couponRepo.save(coupon);
			} else {
				throw new CouponExistsException();
			}
		} else {
			// if it's not equals- throw exception
			throw new CannotUpdateCouponException();
		}
	}

	public void deleteCoupon(int id) throws CannotDeleteCouponException {
		// saving the coupon from db, throws exception if it's not exist
		Coupon c = couponRepo.findById(id).orElseThrow(CannotDeleteCouponException::new);
		// checking if the company id of the coupon is the same as the company that logged in
		if (c.getCompany().getId() == companyID) {
			// runs over all customers and delete the coupon purchase
			for (Customer cust : customerRepo.findAll()) {
				cust.getCoupons().remove(c);
				customerRepo.save(cust);
			}
			// delete the coupon
			couponRepo.deleteById(id);
		} else {
			throw new CannotDeleteCouponException();
		}
	}

	public List<Coupon> getCompanyCoupons() {
		return couponRepo.findCouponByCompanyId(companyID);
	}
	
	public Coupon getOneCompanyCoupon(int id) {
		return couponRepo.findCouponByCompanyIdAndId(companyID, id);
	}

	public List<Coupon> getCompanyCouponsByCategory(CategoryType category) {
		return couponRepo.findCouponByCategoryAndCompanyId(category, companyID);
	}

	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) {
		List<Coupon> coups = new ArrayList<Coupon>();
		// runs over all company's coupons
		for (Coupon coup : couponRepo.findCouponByCompanyId(companyID)) {
			// checking the price
			if (coup.getPrice() <= maxPrice) {
				// add if it's smaller then max price
				coups.add(coup);
			}
		}
		return coups;
	}

	public Company getCompanyDetails() {
		return companyRepo.findById(companyID).orElseThrow();
	}
}
