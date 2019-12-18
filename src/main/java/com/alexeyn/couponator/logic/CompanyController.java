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
        validateCompanyId(company.getCompanyId(), false);
        validateCompanyDoesNotExist(companyDao.findCompanyByCompanyName(company.getCompanyName()));
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
        validateCompanyId(company.getCompanyId(), true);
		validateCompanyExist(company.getCompanyId());
		validateUpdateCompany(company);
        companyDao.save(company);
    }

    public void deleteCompany(Long companyId) throws ApplicationException {
        validateTable();
        validateCompanyExist(companyId);
        companyDao.deleteById(companyId);
    }

    private void validateTable() throws ApplicationException {
        if (companyDao.findTableSize() == 0) {
            throw new ApplicationException(ErrorTypes.EMPTY_TABLE,
                    DateUtils.getCurrentDateAndTime() + ": Empty company table");
        }
    }

	private void validateCompanyId(Long companyId, boolean isRequired) throws ApplicationException {
		if (isRequired) {
			if (companyId == null) {
				throw new ApplicationException(ErrorTypes.NULL_DATA,
						DateUtils.getCurrentDateAndTime() + ": customerId is not supplied");
			}
		} else {
			if (companyId != null) {
				throw new ApplicationException(ErrorTypes.REDUNDANT_DATA,
						DateUtils.getCurrentDateAndTime() + ": companyId is redundant");
			}
		}
	}

    private void validateCompanyExist(Long companyId) throws ApplicationException {
        if (!companyDao.findById(companyId).isPresent()) {
            throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Company doesn't exist");
        }
    }

    private void validateCompanyDoesNotExist(Company company) throws ApplicationException {
        if (company != null) {
            throw new ApplicationException(ErrorTypes.COMPANY_ALREADY_EXIST,
                    DateUtils.getCurrentDateAndTime() + ": Company already exist");
        }
    }

    private void validateUpdateCompany(Company updatedCompany) throws ApplicationException {
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

    boolean isCompanyExist(Long companyId) {
        return companyDao.findById(companyId).isPresent();
    }
}
