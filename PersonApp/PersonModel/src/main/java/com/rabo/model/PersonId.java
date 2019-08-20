/**
 * 
 */
package com.rabo.model;

import java.io.Serializable;

/**
 * @author GangavRK
 *
 */
public class PersonId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fName;
	private String lName;

	public PersonId() {
		super();
	}

	public PersonId(String fName, String lName) {
		this.fName = fName;
		this.lName = lName;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonId other = (PersonId) obj;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equalsIgnoreCase(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equalsIgnoreCase(other.lName))
			return false;
		return true;
	}

}
