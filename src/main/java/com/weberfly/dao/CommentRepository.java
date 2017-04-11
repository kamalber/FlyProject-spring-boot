package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
