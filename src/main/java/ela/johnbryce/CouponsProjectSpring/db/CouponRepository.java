package ela.johnbryce.CouponsProjectSpring.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ela.johnbryce.CouponsProjectSpring.beans.CategoryType;
import ela.johnbryce.CouponsProjectSpring.beans.Coupon;

@Component
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	List<Coupon> findCouponByCompanyId(int id);
	Coupon findCouponByCompanyIdAndId(int companyId, int id);
	Coupon findCouponByCompanyIdAndTitle(int companyId, String title);
	List<Coupon> findCouponByCategoryAndCompanyId(CategoryType category, int id);

}
