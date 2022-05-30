package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CannotDeleteCouponException extends Exception {

	public CannotDeleteCouponException() {
		super("cannot delete coupon...");
	}
}
