package ela.johnbryce.CouponsProjectSpring.Web;

import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ela.johnbryce.CouponsProjectSpring.facades.AdminFacade;
import ela.johnbryce.CouponsProjectSpring.facades.CompanyFacade;
import ela.johnbryce.CouponsProjectSpring.facades.CustomerFacade;

@Configuration
@Aspect
public class AspectConfig {

	@Autowired
	private Map<String, OurSession> sessions;

	@Around("execution(* ela.johnbryce.CouponsProjectSpring.Web.AdminController.*(..))")
	public ResponseEntity<?> adminPermission(ProceedingJoinPoint point) throws Throwable {
		String token = (String) point.getArgs()[0];
		// create a session that contains our ClientFacade identified by its token and
		// lastAccessedTime
		OurSession session = sessions.get(token);
		if (session != null && session.getFacade() instanceof AdminFacade) {
			AdminFacade admin = (AdminFacade) session.getFacade();
			if (admin != null) {
				long last = session.getLastAccessTime();
				if (System.currentTimeMillis() - last < 1000 * 60 * 30) {
					sessions.get(token).setLastAccessTime(System.currentTimeMillis());
					return (ResponseEntity<?>) point.proceed();
				} else {
					sessions.remove(token); // this token has to be received from the Controller itself
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time's Out!");
				}
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@Around("execution(* ela.johnbryce.CouponsProjectSpring.Web.CompanyController.*(..))")
	public ResponseEntity<?> companyPermission(ProceedingJoinPoint point) throws Throwable {
		String token = (String) point.getArgs()[0];
		OurSession session = sessions.get(token);
		if (session != null && session.getFacade() instanceof CompanyFacade) {
			CompanyFacade company = (CompanyFacade) session.getFacade();
			if (company != null) {
				long last = session.getLastAccessTime();
				if (System.currentTimeMillis() - last < 1000 * 60 * 30) {
					sessions.get(token).setLastAccessTime(System.currentTimeMillis());
					return (ResponseEntity<?>) point.proceed();
				} else {
					sessions.remove(token); // this token has to be received from the Controller itself
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time's Out!");
				}
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@Around("execution(* ela.johnbryce.CouponsProjectSpring.Web.CustomerController.*(..))")
	public ResponseEntity<?> customerPermission(ProceedingJoinPoint point) throws Throwable {
		String token = (String) point.getArgs()[0];
		OurSession session = sessions.get(token);
		if (session != null && session.getFacade() instanceof CustomerFacade) {
			CustomerFacade customer = (CustomerFacade) session.getFacade();
			if (customer != null) {
				long last = session.getLastAccessTime();
				if (System.currentTimeMillis() - last < 1000 * 60 * 30) {
					sessions.get(token).setLastAccessTime(System.currentTimeMillis());
					return (ResponseEntity<?>) point.proceed();
				} else {
					sessions.remove(token); // this token has to be received from the Controller itself
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time's Out!");
				}
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
