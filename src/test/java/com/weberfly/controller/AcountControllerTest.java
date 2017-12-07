package com.weberfly.controller;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.weberfly.dao.UserRepository;
import com.weberfly.entities.User;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)

public class AcountControllerTest {



	   @Autowired
	   private TestEntityManager entityManager;

	   @Autowired
	   private UserRepository arrivalRepository;

	

	   @Test
	   public void whenFindAllById() {
	       //given
	       User arrival = new User();
	       arrival.setPassword("kamal");
	       arrival.setFullName("bberriga");
	       entityManager.persist(arrival);
	       entityManager.flush();

	       //when
	       User testArrival = arrivalRepository.findOne(arrival.getId());

	       //then
	       assertThat(testArrival.getFullName()).isEqualTo(arrival.getFullName());
	   }
	}