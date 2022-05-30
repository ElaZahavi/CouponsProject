package ela.johnbryce.CouponsProjectSpring.Web;

import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ela.johnbryce.CouponsProjectSpring.exceptions.IncorrectEmailOrPasswordException;
import ela.johnbryce.CouponsProjectSpring.facades.ClientFacade;
import ela.johnbryce.CouponsProjectSpring.login.ClientType;
import ela.johnbryce.CouponsProjectSpring.login.LoginManager;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private Map<String, OurSession> sessionsMap;

	@PostMapping("/login/{email}/{password}/{type}")
	public ResponseEntity<String> login(@PathVariable String email, @PathVariable String password, @PathVariable ClientType type) {
		String token = UUID.randomUUID().toString();
		ClientFacade service;
		try {
			service = loginManager.login(email, password, type);
			if (service != null) {
				OurSession session = new OurSession(System.currentTimeMillis(), service);
				sessionsMap.put(token, session);
				return ResponseEntity.ok(token);
			}
		} catch (IncorrectEmailOrPasswordException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/logout/{token}")
	public ResponseEntity<String> logout(@PathVariable String token) {
		sessionsMap.remove(token);
		return ResponseEntity.ok("BYE BYE");
	}
}
