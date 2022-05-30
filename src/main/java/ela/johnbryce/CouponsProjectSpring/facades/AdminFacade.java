package ela.johnbryce.CouponsProjectSpring.facades;


import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ela.johnbryce.CouponsProjectSpring.beans.Company;
import ela.johnbryce.CouponsProjectSpring.beans.Coupon;
import ela.johnbryce.CouponsProjectSpring.beans.Customer;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotUpdateCompanyException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CannotUpdateCustomerException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CompanyExistsException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CompanyNotExistException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CustomerExistsException;
import ela.johnbryce.CouponsProjectSpring.exceptions.CustomerNotExistException;
import ela.johnbryce.CouponsProjectSpring.exceptions.IncorrectEmailOrPasswordException;
import ela.johnbryce.CouponsProjectSpring.exceptions.MissingDetailsException;

@Service
@Scope("prototype")
public class AdminFacade extends ClientFacade {
	
	@Override
	public boolean login(String email, String password) throws IncorrectEmailOrPasswordException {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		} else {
			throw new IncorrectEmailOrPasswordException();
		}
	}
	
	public void addCompany(Company company) throws CompanyExistsException, MissingDetailsException {
		if (company.getEmail() == null || company.getEmail().equals("") || company.getPassword() == null ||company.getPassword().equals("") 
				|| company.getName() == null || company.getName().equals("")) {
			throw new MissingDetailsException();
		} else if (companyRepo.findCompanyByEmail(company.getEmail()) != null || companyRepo.findCompanyByName(company.getName()) != null){
			throw new CompanyExistsException();

		} else {
			companyRepo.save(company);
		}
	}
	
	public void updateCompany(Company company) throws CannotUpdateCompanyException, CompanyExistsException {
		boolean exist = false;
		//save the company only if company id already exist
		if (companyRepo.existsById(company.getId())) {
			//runs over all companies and check if email already exist
			for (Company comp : companyRepo.findAll()) {
				if(comp.getEmail().equals(company.getEmail()) && comp.getId() != company.getId()) {
					exist = true;
					break;
				}
			}
			if (exist == false) {
				companyRepo.save(company);
			} else {
				throw new CompanyExistsException();
			}
		} else {
			throw new CannotUpdateCompanyException();
		}
	}
	
	public void deleteCompany(int id) throws CannotUpdateCustomerException, CustomerExistsException {
		//runs over all company's coupons
		for (Coupon coup : couponRepo.findCouponByCompanyId(id)) {
			//runs over all customers and delete every comapny's coupon
			for (Customer cust : customerRepo.findAll()) {
				cust.getCoupons().remove(coup);
				//update in db
				customerRepo.save(cust);
			}
			//delete company's coupons
			couponRepo.deleteById(coup.getId());
		}
		//delete company
		companyRepo.deleteById(id);
	}

	public List<Company> getAllCompanies() {
		return companyRepo.findAll();
	}
	
	public Company getOneCompany(int id) throws CompanyNotExistException {
		//throws custom exception if the input id not exist
		return companyRepo.findById(id).orElseThrow(CompanyNotExistException::new);
	}
	
	public void addCustomer(Customer customer) throws CustomerExistsException, MissingDetailsException {
		//checking if the new customer's email already exist
		if (customer.getEmail() == null || customer.getEmail().equals("") || customer.getPassword() == null || customer.getPassword().equals("")
				|| customer.getFirstName() == null || customer.getFirstName().equals("") || customer.getLastName() == null 
				|| customer.getLastName().equals("")) {
			throw new MissingDetailsException();
		} else if (customerRepo.findCustomerByEmail(customer.getEmail()) != null) {
			//if it's not null don't add the customer and throw exception
			throw new CustomerExistsException();
		} else {
			customerRepo.save(customer);
		}
	}
	

	public void updateCustomer (Customer customer) throws CannotUpdateCustomerException, CustomerExistsException {
		boolean exist = false;
		//saving the customer only if customer id already exist
		if (customerRepo.existsById(customer.getId())) {
			//runs over all customers and check if email already exist
			for (Customer cust : customerRepo.findAll()) {
				if(cust.getEmail().equals(customer.getEmail()) && cust.getId() != customer.getId()) {
					exist = true;
					break;
				}
			}
			if(exist == false) {
				customerRepo.save(customer);
			} else {
				throw new CustomerExistsException();
			}
		} else {
			throw new CannotUpdateCustomerException();
		}
	}
	
	public void deleteCustomer(int id) {
		//clear customer's coupons
		customerRepo.findById(id).get().getCoupons().clear();
		//delete customer
		customerRepo.deleteById(id);
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}
	
	public Customer getOneCustomer(int id) throws CustomerNotExistException {
		//throws "no such element" exception
		return customerRepo.findById(id).orElseThrow(CustomerNotExistException::new);
	}
}


