/**
 * 
 */
package com.rabo.circuithealth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.rabo.circuitswitch.CircuitSwitch;

/**
 * @author GangavRK
 *
 */
@Component
public class CircuitHealth implements HealthIndicator {

	@Autowired
	CircuitSwitch circuitSwitch;

	@Override
	public Health health() {

		return Health.up().withDetail("Circuit Status", circuitSwitch.circuitBreaker.getState()).build();
	}
}
