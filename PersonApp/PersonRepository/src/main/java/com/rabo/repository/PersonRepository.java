package com.rabo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rabo.model.Person;
import com.rabo.model.PersonId;

@Repository
public interface PersonRepository extends CrudRepository<Person, PersonId> {

	@Query(value = "select * from person where firstname=?1 and lastname=?2", nativeQuery = true)
	Person findByPrimKey(@Param("fName") String fName, @Param("lName") String lName);
}
