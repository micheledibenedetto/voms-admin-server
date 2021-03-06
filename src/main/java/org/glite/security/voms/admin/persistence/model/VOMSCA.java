/**
 * Copyright (c) Members of the EGEE Collaboration. 2006-2009.
 * See http://www.eu-egee.org/partners/ for details on the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors:
 * 	Andrea Ceccanti (INFN)
 */
package org.glite.security.voms.admin.persistence.model;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.glite.security.voms.admin.util.DNUtil;

public class VOMSCA implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -2633375466574044765L;

	Short id;

	String subjectString;

	String description;

	Date creationTime;

	public VOMSCA() {

		// TODO Auto-generated constructor stub
	}

	public VOMSCA(X509Certificate cert, String description) {

		assert cert != null : "X509Certificate is null!";

		subjectString = DNUtil.getOpenSSLSubject(cert.getSubjectX500Principal());
		creationTime = new Date();

		this.description = description;
	}

	public VOMSCA(String name, String desc) {

		this.subjectString = name;
		this.description = desc;
		creationTime = new Date();

	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * @return Returns the dn.
	 */
	public String getSubjectString() {

		return subjectString;
	}

	/**
	 * @param dn
	 *            The dn to set.
	 */
	public void setSubjectString(String dn) {

		this.subjectString = dn;
	}

	/**
	 * @return Returns the id.
	 */
	public Short getId() {

		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Short id) {

		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return subjectString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {

		if (this == other)
			return true;

		if (!(other instanceof VOMSCA))
			return false;

		if (other == null)
			return false;

		final VOMSCA that = (VOMSCA) other;
		return (getSubjectString().equals(that.getSubjectString()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return subjectString.hashCode();

	}

	public String getShortName() {
		return subjectString;
	}

	@Deprecated
	public String getDn() {

		return subjectString;
	}

	@Deprecated
	public void setDn(String dn) {

		this.subjectString = dn;
	}

	public Date getCreationTime() {

		return creationTime;
	}

	public void setCreationTime(Date creationTime) {

		this.creationTime = creationTime;
	}

}
