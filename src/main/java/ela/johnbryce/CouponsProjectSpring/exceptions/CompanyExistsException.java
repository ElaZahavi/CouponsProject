package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CompanyExistsException extends Exception {

	public CompanyExistsException() {
		super("Company alredy exists...");
	}	
}
