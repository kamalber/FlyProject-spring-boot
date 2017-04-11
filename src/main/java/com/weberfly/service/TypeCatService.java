package com.weberfly.service;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.TypeCatRepository;
import com.weberfly.entities.Type_category;

@Service
public class TypeCatService {
	
@Autowired
private TypeCatRepository typeCatRepository;
	
public void saveType(Type_category typeCategory) {
	typeCatRepository.save(typeCategory);
}
public List<Type_category> getAllType() {
	return typeCatRepository.findAll();
}
public Type_category findType(Long id) {
	return typeCatRepository.findOne(id);
}

}
