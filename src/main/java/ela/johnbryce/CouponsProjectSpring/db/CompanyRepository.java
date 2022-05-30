package ela.johnbryce.CouponsProjectSpring.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ela.johnbryce.CouponsProjectSpring.beans.Company;

@Component
public interface CompanyRepository extends JpaRepository<Company, Integer>{
	
	Company findCompanyByEmailAndPassword(String email, String password);
	Company findCompanyByEmail(String email);
	Company findCompanyByName(String name);



}
