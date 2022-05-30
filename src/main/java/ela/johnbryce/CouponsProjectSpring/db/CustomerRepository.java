package ela.johnbryce.CouponsProjectSpring.db;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ela.johnbryce.CouponsProjectSpring.beans.Customer;

@Component
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findCustomerByEmailAndPassword(String email, String password);
	Customer findCustomerByEmail(String email);
}
