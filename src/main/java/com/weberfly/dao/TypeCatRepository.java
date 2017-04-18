package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.TypeCategory;

@Repository
public interface TypeCatRepository extends JpaRepository<TypeCategory, Long>{

}
