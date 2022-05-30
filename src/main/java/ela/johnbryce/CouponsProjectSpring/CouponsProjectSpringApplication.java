package ela.johnbryce.CouponsProjectSpring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ela.johnbryce.CouponsProjectSpring.thread.CouponExpirationDailyJob;

@SpringBootApplication
public class CouponsProjectSpringApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext ctx = SpringApplication.run(CouponsProjectSpringApplication.class, args);
		
		CouponExpirationDailyJob thread = ctx.getBean(CouponExpirationDailyJob.class);
		
		thread.start();
		
	}
}
