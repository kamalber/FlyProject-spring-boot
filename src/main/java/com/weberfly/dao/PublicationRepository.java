package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Publication;
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
