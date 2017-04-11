package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
