package com.alexeyn.couponator.logic;

import java.util.List;

import com.alexeyn.couponator.entities.Company;
import com.alexeyn.couponator.dao.ICompanyDao;
import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CompanyController {

    @Autowired
    private ICompanyDao companyDao;


    public long createCompany(Company company) throws ApplicationException {
        validateCompany(company);
        validateCompanyId(company.getCompanyId());
        validateCompanyDoesNotExist(companyDao.findCompanyId(company.getCompanyName()));
        return companyDao.save(company).getCompanyId();
    }

    public Company getCompany(long companyId) throws ApplicationException {
        validateTable();
        validateCompanyExist(companyId);
        return companyDao.findById(companyId).get();
    }

    public List<Company> getAllCompanies() throws ApplicationException {
//		validateTable();
        return (List<Company>) companyDao.findAll();
    }

    public void updateCompany(Company company) throws ApplicationException {
        validateTable();
        validateCompany(company);
        validateUpdateCompany(company);
        validateCompanyExist(companyDao.findCompanyId(company.getCompanyName()));
        companyDao.save(company);
    }

    public void deleteCompany(long companyId) throws ApplicationException {
        validateTable();
        validateCompanyExist(companyId);
        companyDao.deleteById(companyId);
    }

    private void validateTable() throws ApplicationException {
        List<Company> companies = (List<Company>) companyDao.findAll();
        if (companies == null) {
            throw new ApplicationException(ErrorTypes.EMPTY_TABLE,
                    DateUtils.getCurrentDateAndTime() + ": Empty company table");
        }
    }

    private void validateCompanyExist(long companyId) throws ApplicationException {
        if (!companyDao.findById(companyId).isPresent()) {
            throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Company doesn't exist");
        }
    }

    private void validateCompanyDoesNotExist(long companyId) throws ApplicationException {
        if (companyDao.findById(companyId).isPresent()) {
            throw new ApplicationException(ErrorTypes.COMPANY_ALREADY_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Company already exist");
        }
    }

    private void validateCompanyId(Long companyId) throws ApplicationException {
		if (companyId != null) {
			throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
					DateUtils.getCurrentDateAndTime() + "Id is redundant");
		}
	}

    private void validateUpdateCompany(Company updatedCompany) throws ApplicationException {
        if (updatedCompany.getCompanyId() == null) {
            throw new ApplicationException(ErrorTypes.ID_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Cannot update company id wasn't provided");
        }
        Company currentCompany = companyDao.findById(updatedCompany.getCompanyId()).get();
        if (currentCompany.equals(updatedCompany)) {
            throw new ApplicationException(ErrorTypes.UPDATE_FAILED,
                    DateUtils.getCurrentDateAndTime() + ": No difference found between old and new data");
        }
    }

    private void validateCompany(Company company) throws ApplicationException {
        if (company == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + "Null company");
        }
        if (company.getCompanyName() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + "Null company name");
        }
        if (company.getCompanyName().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA,
                    DateUtils.getCurrentDateAndTime() + "Empty company name");
        }
        if (company.getAddress() == null) {
            throw new ApplicationException(ErrorTypes.NULL_DATA,
                    DateUtils.getCurrentDateAndTime() + "Null company email");
        }
        if (company.getAddress().isEmpty()) {
            throw new ApplicationException(ErrorTypes.EMPTY_DATA,
                    DateUtils.getCurrentDateAndTime() + "Empty company email");
        }
    }
}
