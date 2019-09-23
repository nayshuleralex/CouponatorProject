package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ICompanyDao extends CrudRepository<Company, Long> {

    @Query("SELECT c.companyId FROM Companies c WHERE c.companyName =: companyName")
    Long findCompanyId(String companyName);

}
