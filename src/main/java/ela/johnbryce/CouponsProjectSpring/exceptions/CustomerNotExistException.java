package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CustomerNotExistException extends Exception{
	
	public CustomerNotExistException() {
		super("customer does not exist...");
	}
}
