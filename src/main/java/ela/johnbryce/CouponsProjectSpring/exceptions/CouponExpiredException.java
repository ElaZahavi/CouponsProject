package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CouponExpiredException extends Exception {

	public CouponExpiredException() {
		super("The coupon has expired...");
	}
}
