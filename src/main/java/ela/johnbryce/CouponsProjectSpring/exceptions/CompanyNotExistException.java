package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class CompanyNotExistException extends Exception {

	public CompanyNotExistException() {
		super("company does not exist...");
	}
}
