package com.weberfly.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.weberfly.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Long>{

    Country findOneByHcKey(String hc_key);
	

}
