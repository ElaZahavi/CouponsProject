package ela.johnbryce.CouponsProjectSpring.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ela.johnbryce.CouponsProjectSpring.db.CompanyRepository;
import ela.johnbryce.CouponsProjectSpring.db.CouponRepository;
import ela.johnbryce.CouponsProjectSpring.db.CustomerRepository;
import ela.johnbryce.CouponsProjectSpring.exceptions.IncorrectEmailOrPasswordException;

@Service
public abstract class ClientFacade {

	@Autowired
	protected CompanyRepository companyRepo;
	@Autowired
	protected CustomerRepository customerRepo;
	@Autowired
	protected CouponRepository couponRepo;
	
	public abstract boolean login(String email, String password) throws IncorrectEmailOrPasswordException;
}
