package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Company;
import com.alexeyn.couponator.dao.ICompanyDao;
import com.alexeyn.couponator.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CompanyController {

	@Autowired
	private ICompanyDao companyDao;


	public long createCompany(Company company) throws ApplicationException {
		Company c = companyDao.save(company);
		return c.getCompanyId();
	}

	public Company getCompany(long companyId) throws ApplicationException {
		return companyDao.findById(companyId).get();
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		return (List<Company>) companyDao.findAll();
	}
	public void updateCompany(Company company) throws ApplicationException {
		companyDao.save(company);
	}

	public void deleteCompany(long companyId) throws ApplicationException {
		companyDao.deleteById(companyId);
	}


	/*public boolean isCompanyExist(String companyName) throws ApplicationException {
		if (this.companyDao.isCompanyExist(companyName)) {
			return true;
		}
		return false;
	}

	public boolean isCompanyExist(long companyId) throws ApplicationException {
		if (this.companyDao.isCompanyExist(companyId)) {
			return true;
		}
		return false;
	}

	public boolean isCompanyTableExist() throws ApplicationException {
		if (this.companyDao.isCompanyTableExist()) {
			return true;
		}
		return false;
	}

	private void ValidateCompany(Company company) throws ApplicationException {
		if (company == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null company");
		}
		if (company.getCompanyName() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null company name");
		}
		if (company.getCompanyName().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty company name");
		}
		if (company.getAddress() == null) {
			throw new ApplicationException(ErrorTypes.NULL_DATA, "Null company email");
		}
		if (company.getAddress().isEmpty()) {
			throw new ApplicationException(ErrorTypes.EMPTY_DATA, "Empty company email");
		}
	}*/
}
