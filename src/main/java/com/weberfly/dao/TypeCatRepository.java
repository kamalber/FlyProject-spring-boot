package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Type_category;
@Repository
public interface TypeCatRepository extends JpaRepository<Type_category, Long>{

}
