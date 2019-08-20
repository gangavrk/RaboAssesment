/**
 * 
 */
package com.rabo.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.exception.BusinessException;
import com.rabo.model.Person;
import com.rabo.model.PersonId;
import com.rabo.repository.PersonRepository;
import com.rabo.service.IPersonService;

/**
 * @author GangavRK
 *
 */
@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	PersonRepository personRepository;

	public Person findPerson(PersonId personId) {
		Optional<Person> personFetched = personRepository.findById(personId);
		if (personFetched.isPresent()) {
			return personFetched.get();
		} else {
			throw new BusinessException("No records matched");
		}
	}

	public String addPerson(Person person) {
		personRepository.save(person);
		return "OK";

	}

	public String updatePerson(Person person) {
		personRepository.save(person);
		return "OK";
	}

	public String deletePerson(PersonId personId) {
		personRepository.deleteById(personId);
		return "OK";
	}

	public PersonServiceImpl() {
		super();
	}

}
