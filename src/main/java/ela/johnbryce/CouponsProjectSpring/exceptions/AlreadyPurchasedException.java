package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class AlreadyPurchasedException extends Exception {

	public AlreadyPurchasedException() {
		super("You already bought this coupon...");
	}
}
