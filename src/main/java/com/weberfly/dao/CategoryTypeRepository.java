package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.weberfly.entities.TypeCategory;

public interface CategoryTypeRepository  extends JpaRepository<TypeCategory, Long>{

	public TypeCategory findByName(String Name);
}
