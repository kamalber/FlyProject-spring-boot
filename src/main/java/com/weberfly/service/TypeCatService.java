package com.weberfly.service;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.TypeCatRepository;
import com.weberfly.entities.TypeCategory;

@Service
public class TypeCatService {
	
@Autowired
private TypeCatRepository typeCatRepository;
	
public void saveType(TypeCategory typeCategory) {
	typeCatRepository.save(typeCategory);
}
public List<TypeCategory> getAllType() {
	return typeCatRepository.findAll();
}
public TypeCategory findType(Long id) {
	return typeCatRepository.findOne(id);
}

}
