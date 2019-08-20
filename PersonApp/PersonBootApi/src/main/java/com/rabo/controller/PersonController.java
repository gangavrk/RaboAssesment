package com.rabo.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.circuitswitch.CircuitSwitch;
import com.rabo.exception.BusinessException;
import com.rabo.exception.PersonAlreadyExistException;
import com.rabo.exception.PersonNotFoundException;
import com.rabo.model.Person;
import com.rabo.model.PersonId;
import com.rabo.service.IPersonService;

/**
 * @author GangavRK
 *
 */
@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	IPersonService iPersonService;
	@Autowired
	CircuitSwitch circuitSwitch;

	@Valid
	@RequestMapping(value = "/addPerson", method = RequestMethod.POST, produces = "application/text", consumes = "application/json")
	public ResponseEntity<Object> addPerson(@RequestBody @NotNull Person person) {

		PersonId personId = new PersonId();
		if (person.getfName() == null || person.getlName() == null) {
			return new ResponseEntity<Object>("Mandatory data is missing", HttpStatus.BAD_REQUEST);
		}
		personId.setfName(person.getfName());
		personId.setlName(person.getlName());
		Person fetchedPerson = null;
		PersonAlreadyExistException personExistsExcep;

		try {
			fetchedPerson = iPersonService.findPerson(personId);
		} catch (BusinessException busiExcep) {
			// Ignore
		}
		if (fetchedPerson == null) {
			iPersonService.addPerson(person);
			return new ResponseEntity<Object>("Created succesfully", HttpStatus.OK);
		} else {
			try {
				personExistsExcep = new PersonAlreadyExistException("Person already exists");
				return new ResponseEntity<Object>(personExistsExcep.getMessage(), HttpStatus.CONFLICT);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@Valid
	@RequestMapping(value = "/getPerson/{fName}/{lName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPerson(@PathVariable("fName") String fName, @PathVariable("lName") String lName) {

		Person fetchedPerson = null;
		PersonNotFoundException perNotFoundException = null;
		try {
			PersonId personId = new PersonId(fName, lName);
			fetchedPerson = circuitSwitch.findPerson(personId);
		} catch (BusinessException busiExcep) {
			perNotFoundException = new PersonNotFoundException("No matching person found");
		}
		if (fetchedPerson != null) {
			return new ResponseEntity<Object>(fetchedPerson, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(perNotFoundException.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@Valid
	@RequestMapping(value = "/updatePerson", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updatePerson(@RequestBody @NotNull Person person) {

		PersonId personId = new PersonId();
		if (person.getfName() == null || person.getlName() == null) {
			return new ResponseEntity<Object>("Mandatory data is missing", HttpStatus.BAD_REQUEST);
		}
		personId.setfName(person.getfName());
		personId.setlName(person.getlName());
		Person fetchedPerson = null;
		PersonNotFoundException perNotFoundException = null;
		try {
			fetchedPerson = iPersonService.findPerson(personId);
		} catch (BusinessException busiExcep) {
			perNotFoundException = new PersonNotFoundException("No matching person found");
		}
		if (fetchedPerson != null) {
			iPersonService.updatePerson(person);
			return new ResponseEntity<Object>(person, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(perNotFoundException.getMessage(), HttpStatus.CONFLICT);
		}

	}

	@Valid
	@RequestMapping(value = "/getPerson", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> getPerson(@RequestBody @NotNull PersonId personId) {

		Person fetchedPerson = null;
		PersonNotFoundException perNotFoundException = null;
		if (personId.getfName() == null || personId.getlName() == null) {
			return new ResponseEntity<Object>("Mandatory data is missing", HttpStatus.BAD_REQUEST);
		}
		try {
			fetchedPerson = circuitSwitch.findPerson(personId);
		} catch (BusinessException busiExcep) {
			perNotFoundException = new PersonNotFoundException("No matching person found");
		}
		if (fetchedPerson != null) {
			return new ResponseEntity<Object>(fetchedPerson, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(perNotFoundException.getMessage(), HttpStatus.CONFLICT);
		}
	}

}