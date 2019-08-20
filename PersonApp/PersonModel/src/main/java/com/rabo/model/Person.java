/**
 * 
 */
package com.rabo.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author GangavRK
 *
 */

@Entity
@IdClass(PersonId.class)
@Table(name = "person")
public class Person {

	@Id
	@Column(name = "firstname")
	private String fName;
	@Id
	@Column(name = "lastname")
	private String lName;
	@Column(name = "age")
	private int age;
	@Column(name = "dateofbirth")
	private Date dateOfBirth;
	@Column(name = "address")
	String address;

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "{\"fName\":\"" + fName + "\",\"lName:\"" + lName + "\",\"age:\"" + age + "\",\"dateOfBirth:\""
				+ dateOfBirth + "\",\"address:\"" + address + "}";
	}
}
