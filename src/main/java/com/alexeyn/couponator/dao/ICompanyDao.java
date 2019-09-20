package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Company;
import org.springframework.data.repository.CrudRepository;

public interface ICompanyDao extends CrudRepository<Company, Long> {

    /*long createCompany(Company company);

    Company getCompany(long companyId);

    void updateCompany(Company company);

    boolean isCompanyExistsById(long companyId);

    void deleteCompany(long id);

    boolean isCompanyExistsByName(String companyName);

    void getAllCompanies();*/

}
