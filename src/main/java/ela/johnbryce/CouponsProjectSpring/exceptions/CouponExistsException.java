package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CouponExistsException extends Exception {

	public CouponExistsException() {
		super("Coupon already exists...");
	}
}
