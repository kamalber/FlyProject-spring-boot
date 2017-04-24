package com.weberfly.service;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.weberfly.dao.CategoryTypeRepository;
import com.weberfly.entities.TypeCategory;

@Service
public class TypeCategoryService {
	
@Autowired
private CategoryTypeRepository typeCatRepository;
	
public void save(TypeCategory typeCategory) {
	typeCatRepository.save(typeCategory);
}
public List<TypeCategory> findAll() {
	return typeCatRepository.findAll();
}
public TypeCategory find(Long id) {
	return typeCatRepository.findOne(id);
}
public void typeCatService(TypeCategory typeCategory) {

	
}
public Boolean isExist(TypeCategory typeCategory){
	return typeCatRepository.findByName(typeCategory.getName()) != null;
}
public void delete(Long id){
	typeCatRepository.delete(id);
}
}
