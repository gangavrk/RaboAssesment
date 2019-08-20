package com.rabo.service;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rabo.model.Person;
import com.rabo.model.PersonId;
import com.rabo.repository.PersonRepository;
import com.rabo.serviceimpl.PersonServiceImpl;

import junit.framework.Assert;

/**
 * @author GangavRK
 *
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class PersonServiceTest {

	private static Person person;
	private static PersonId personId;

	@InjectMocks
	IPersonService iPersonService = new PersonServiceImpl();

	@Mock
	PersonRepository personRepo;

	@Order(2)
	@Test
	public void testGetPerson() {
		Person personReturned = iPersonService.findPerson(personId);
		Assert.assertEquals(person.getAge(), personReturned.getAge());
	}

	@Order(1)
	@Test
	public void testAddPerson() {
		String status = iPersonService.addPerson(person);
		Assert.assertEquals("OK", status);
	}

	@Order(3)
	@Test
	public void testUpdatePerson() {
		person.setAddress("Amsterdam");
		String status = iPersonService.updatePerson(person);
		Assert.assertEquals("OK", status);
	}

	@BeforeEach
	void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		Mockito.lenient().when(personRepo.findById(Mockito.any())).thenReturn(Optional.of(person));
		Mockito.lenient().when(personRepo.save(Mockito.any())).thenReturn((person));
	}

	@BeforeAll
	void getTestPerson() {
		person = new Person();
		person.setfName("Test10");
		person.setlName("Case10");
		person.setAge(1);
		person.setAddress("Rotterdam");
		person.setDateOfBirth(new Date(19860101));

		personId = new PersonId("Test10", "case10");

	}
}
