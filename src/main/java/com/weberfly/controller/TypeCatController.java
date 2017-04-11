package com.weberfly.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.weberfly.entities.Type_category;
import com.weberfly.service.TypeCatService;

@RestController
public class TypeCatController {
	
@Autowired
 private TypeCatService typeCatService;
	
@RequestMapping(value="/type",method=RequestMethod.POST)
	 public void saveType(@RequestBody Type_category type_category) {
		typeCatService.saveType(type_category);
		}
 @RequestMapping(value="/types",method=RequestMethod.GET)
 public List<Type_category> getAllType(){
	 return typeCatService.getAllType();
 }
 @RequestMapping(value="/types/{id}",method=RequestMethod.GET)
 public Type_category findType(@PathVariable(name="id") Long id) {
		return typeCatService.findType(id);
	}
 
}
