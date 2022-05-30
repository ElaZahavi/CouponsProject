package ela.johnbryce.CouponsProjectSpring.thread;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ela.johnbryce.CouponsProjectSpring.beans.Coupon;
import ela.johnbryce.CouponsProjectSpring.beans.Customer;
import ela.johnbryce.CouponsProjectSpring.db.CouponRepository;
import ela.johnbryce.CouponsProjectSpring.db.CustomerRepository;

@Component
public class CouponExpirationDailyJob extends Thread {
	
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomerRepository custRepo;
	
	private boolean quit;
	
	@Override
	public void run() {
		while (!quit) {
			try {
				Calendar cal = Calendar.getInstance();
				//runs over all coupons
				for (Coupon coupon : coupRepo.findAll()) {
					//checking if the coupon has expired
					if(coupon.getEndDate().before(cal.getTime())) {
						//runs over all customers
						for (Customer cust : custRepo.findAll()) {
							//remove from customer's coupons the expired coupon
							cust.getCoupons().remove(coupon);
							//update customer
							custRepo.save(cust);
						}
						coupRepo.delete(coupon);
					}
				}
				//sleeps for 24 hours
				Thread.sleep(1000*60*60*24);
				
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void stopRunning() {
		quit = true;
		this.interrupt();
	}
}
