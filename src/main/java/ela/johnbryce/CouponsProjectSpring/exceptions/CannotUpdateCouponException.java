package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CannotUpdateCouponException extends Exception {
	
	public CannotUpdateCouponException() {
		super("Cannot update coupon...");
	}

}
