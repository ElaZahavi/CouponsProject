package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CouponIsOutOfStockException extends Exception {

	public CouponIsOutOfStockException() {
		super("The coupon is out of stock...");
	}
}
