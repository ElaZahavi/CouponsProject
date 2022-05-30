package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CustomerExistsException extends Exception {

	public CustomerExistsException() {
		super("Customer alredy exists...");
	}
	
	

}
