package ela.johnbryce.CouponsProjectSpring.exceptions;

@SuppressWarnings("serial")
public class MissingDetailsException extends Exception {

	public MissingDetailsException() {
		super("You must fill in all fields");
	}
}
