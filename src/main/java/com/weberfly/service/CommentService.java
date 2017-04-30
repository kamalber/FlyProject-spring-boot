package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CommentRepository;

import com.weberfly.entities.Comment;


@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	public void save(Comment comment) {
		commentRepository.save(comment);
	}
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}
	public Comment find(Long id) {
		return commentRepository.findOne(id);
	}
	
	public void delete(Long id){
		commentRepository.delete(id);
	}
}
