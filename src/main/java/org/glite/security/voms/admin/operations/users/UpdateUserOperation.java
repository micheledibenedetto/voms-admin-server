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
package org.glite.security.voms.admin.operations.users;

import org.glite.security.voms.admin.operations.BaseVoRWOperation;
import org.glite.security.voms.admin.persistence.dao.VOMSUserDAO;
import org.glite.security.voms.admin.persistence.model.VOMSUser;

public class UpdateUserOperation extends BaseVoRWOperation {

	VOMSUser user;
	String name;
	String surname;
	String institution;
	String address;
	String phoneNumber;
	String emailAddress;

	private UpdateUserOperation(VOMSUser u){
		this.user = u;
	}
	private UpdateUserOperation(VOMSUser u, String name,
			String surname, String institution, String address,
			String phoneNumber, String emailAddress) {

		user = u;
		this.name = name;
		this.surname = surname;
		this.institution = institution;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;

	}

	
	public Object doExecute() {

		if (name!= null)
			user.setName(name);
		
		if (surname != null)
			user.setSurname(surname);
		
		if (institution != null)
			user.setInstitution(institution);
		
		if (address != null)
			user.setAddress(address);
		
		if (phoneNumber != null)
			user.setPhoneNumber(phoneNumber);
		
		if (emailAddress != null)
			user.setEmailAddress(emailAddress);
		
		VOMSUserDAO.instance().update(user);
		return null;
	}

	public static UpdateUserOperation instance(VOMSUser usr, String name,
			String surname, String institution, String address,
			String phoneNumber, String emailAddress) {

		return new UpdateUserOperation(usr,name, surname, institution,address, phoneNumber, emailAddress);
	}
	
	public static UpdateUserOperation instance(VOMSUser u){
		return new UpdateUserOperation(u);
	}

}
