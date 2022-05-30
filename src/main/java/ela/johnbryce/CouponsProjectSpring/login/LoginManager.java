package ela.johnbryce.CouponsProjectSpring.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import ela.johnbryce.CouponsProjectSpring.exceptions.IncorrectEmailOrPasswordException;
import ela.johnbryce.CouponsProjectSpring.facades.AdminFacade;
import ela.johnbryce.CouponsProjectSpring.facades.ClientFacade;
import ela.johnbryce.CouponsProjectSpring.facades.CompanyFacade;
import ela.johnbryce.CouponsProjectSpring.facades.CustomerFacade;

@Component
public class LoginManager {
	
	@Autowired
	private ConfigurableApplicationContext ctx;
	
	public ClientFacade login(String email, String password, ClientType clientType) throws IncorrectEmailOrPasswordException {

		switch (clientType) {
		
		case Administrator:
			AdminFacade admin = ctx.getBean(AdminFacade.class);
			if(admin.login(email, password)) {
				return admin;
			} 
			
		case Company:
			CompanyFacade company = ctx.getBean(CompanyFacade.class);
			if (company.login(email, password)) {				
				return company;
			}
			
		case Customer:
			CustomerFacade customer = ctx.getBean(CustomerFacade.class);
			if (customer.login(email, password)) {
				return customer;
			}
		}
		//returns null if it's not one of the cases
		return null;
	}

}
