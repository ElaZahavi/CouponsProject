package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class IncorrectEmailOrPasswordException extends Exception {
	
	public IncorrectEmailOrPasswordException() {
		super("Incorrect email or password...");
	}

}
