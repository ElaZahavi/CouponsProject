package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CannotUpdateCustomerException extends Exception {
	
	public CannotUpdateCustomerException() {
		super("Cannot update customer...");
	}

}
