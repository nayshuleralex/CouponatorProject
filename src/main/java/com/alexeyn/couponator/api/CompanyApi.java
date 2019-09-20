package com.alexeyn.couponator.api;

import com.alexeyn.couponator.entities.Company;
import com.alexeyn.couponator.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alexeyn.couponator.logic.CompanyController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyApi {
	
	@Autowired
	private CompanyController companyController;

	// method = POST	url = http://localhost:8080/companies
	@PostMapping
	public long createCompany(@RequestBody Company company) throws ApplicationException {
		return this.companyController.createCompany(company);
	}

	// method = GET		url = http://localhost:8080/companies/42
	@GetMapping("/{companyId}")
	public Company getCompany(@PathVariable("companyId") long id) throws ApplicationException {
		return this.companyController.getCompany(id);
	}

	// method = GET		url = http://localhost:8080/companies
	@GetMapping
	public List<Company> getAllCompanies() throws ApplicationException {
		return this.companyController.getAllCompanies();
	}

	// method = PUT		url = http://localhost:8080/companies
	@PutMapping
	public void updateCompany(@RequestBody Company company) throws ApplicationException {
		this.companyController.updateCompany(company);
	}

	// method = DELETE		url = http://localhost:8080/companies/42
	@DeleteMapping("/{companyId}")
	public void deleteCompany(@PathVariable("companyId") long id) throws ApplicationException {
		this.companyController.deleteCompany(id);
		System.out.println("Delete company : " + id);
	}

	/*public boolean isCompanyExist(String companyName) throws ApplicationException {
		return this.companyController.isCompanyExist(companyName);
	}

	public boolean isCompanyExist(long id) throws ApplicationException {
		return this.companyController.isCompanyExist(id);
	}*/


}
