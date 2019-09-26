package com.alexeyn.couponator.dao;

import com.alexeyn.couponator.entities.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICompanyDao extends CrudRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.companyName = :companyName")
    Company findCompanyByCompanyName(@Param("companyName") String companyName);

}
