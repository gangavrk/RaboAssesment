package com.rabo.service;

import com.rabo.model.PersonId;
import com.rabo.model.Person;

public interface IPersonService {

	public Person findPerson(PersonId personId);

	public String addPerson(Person person);

	public String updatePerson(Person person);

	public String deletePerson(PersonId personId);

}
