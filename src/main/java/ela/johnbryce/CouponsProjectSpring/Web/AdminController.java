
package ela.johnbryce.CouponsProjectSpring.Web;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ela.johnbryce.CouponsProjectSpring.beans.Company;
import ela.johnbryce.CouponsProjectSpring.beans.Customer;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotUpdateCompanyException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotUpdateCustomerException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CompanyExistsException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CompanyNotExistException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CustomerExistsException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CustomerNotExistException;
import ela.johnbryce.CouponsProjectSpring.exceptions.MissingDetailsException;
import ela.johnbryce.CouponsProjectSpring.facades.AdminFacade;

@RestController
@Scope("prototype")
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	Map<String, OurSession> sessionsMap;

	@PostMapping("/company/{token}")
	public ResponseEntity<String> addCompany(@PathVariable String token, @RequestBody Company company) {
		try {
			AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
			admin.addCompany(company);
			return ResponseEntity.ok("Company added!");
		} catch (CompanyExistsException | MissingDetailsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/updateCompany/{token}")
	public ResponseEntity<String> updateCompany(@PathVariable String token, @RequestBody Company company) {
		try {
			AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
			admin.updateCompany(company);
			return ResponseEntity.ok("Company updated");
		} catch (CannotUpdateCompanyException | CompanyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/deleteCompany/{token}/{id}")
	public ResponseEntity<String> deleteCompany(@PathVariable String token, @PathVariable int id) {
		try {
			AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
			admin.deleteCompany(id);
			return ResponseEntity.ok("Company deleted");
		} catch (CannotUpdateCustomerException | CustomerExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@GetMapping("/getAllCompanies/{token}")
	public ResponseEntity<List<Company>> getAllCompanies(@PathVariable String token) {
		AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(admin.getAllCompanies());
	}

	@GetMapping("/getOneCompany/{token}/{id}")
	public ResponseEntity<?> getOneCompany(@PathVariable String token, @PathVariable int id) {
		try {
			AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
			return ResponseEntity.ok(admin.getOneCompany(id));
		} catch (CompanyNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/customer/{token}")
	public ResponseEntity<String> addCustomer(@PathVariable String token, @RequestBody Customer customer) {
		try {
			AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
			admin.addCustomer(customer);
			return ResponseEntity.ok("Customer added");
		} catch (CustomerExistsException | MissingDetailsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/updateCustomer/{token}")
	public ResponseEntity<String> updateCustomer(@PathVariable String token, @RequestBody Customer customer) {
		try {
			AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
			admin.updateCustomer(customer);
			return ResponseEntity.ok("Customer updated");
		} catch (CannotUpdateCustomerException | CustomerExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/deleteCustomer/{token}/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable String token, @PathVariable int id) {
		AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
		admin.deleteCustomer(id);
		return ResponseEntity.ok("Customer deleted");
	}

	@GetMapping("/getAllCustomers/{token}")
	public ResponseEntity<List<Customer>> getAllCustomers(@PathVariable String token) {
		AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
		return ResponseEntity.ok(admin.getAllCustomers());
	}

	@GetMapping("/getOneCustomer/{token}/{id}")
	public ResponseEntity<?> getOneCustomer(@PathVariable String token, @PathVariable int id) {
		try {
			AdminFacade admin = (AdminFacade) sessionsMap.get(token).getFacade();
			return ResponseEntity.ok(admin.getOneCustomer(id));
		} catch (CustomerNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
