/**
 * 
 */
package com.rabo.circuitswitch;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.model.Person;
import com.rabo.model.PersonId;
import com.rabo.serviceimpl.PersonServiceImpl;

/**
 * @author GangavRK
 *
 */
@Service
public class CircuitSwitch {
	@Autowired
	public PersonServiceImpl personServImpl;
	public CircuitBreaker circuitBreaker;

	public CircuitSwitch() {
		CircuitBreakerConfig config = CircuitBreakerConfig.custom().failureRateThreshold(20)
				.waitDurationInOpenState(Duration.ofMillis(2000)).ringBufferSizeInHalfOpenState(5)
				.ringBufferSizeInClosedState(10).build();
		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);
		circuitBreaker = circuitBreakerRegistry.circuitBreaker("person");
	}

	public Person findPerson(PersonId personId) {

		Function<PersonId, Person> funcToGetPerson = (PersonId p) -> {
			return personServImpl.findPerson(p);
		};
		Person perFetched = CircuitBreaker.decorateFunction(circuitBreaker, funcToGetPerson).apply(personId);

		return perFetched;
	}
}
