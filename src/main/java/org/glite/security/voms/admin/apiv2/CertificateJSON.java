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

package org.glite.security.voms.admin.apiv2;

import org.glite.security.voms.admin.persistence.model.Certificate;

public class CertificateJSON {
	
	String subjectString;
	String issuerString;
	
	Boolean suspended;
	String suspensionReason;
	
	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public String getSuspensionReason() {
		return suspensionReason;
	}

	public void setSuspensionReason(String suspensionReason) {
		this.suspensionReason = suspensionReason;
	}

	public String getSubjectString() {
		return subjectString;
	}
	
	public void setSubjectString(String subjectString) {
		this.subjectString = subjectString;
	}
	public String getIssuerString() {
		return issuerString;
	}
	public void setIssuerString(String issuerString) {
		this.issuerString = issuerString;
	}
	
	public static CertificateJSON fromCertificate(Certificate cert){
		
		CertificateJSON c = new CertificateJSON();
		c.setSubjectString(cert.getSubjectString());
		c.setIssuerString(cert.getCa().getSubjectString());
		c.setSuspended(cert.isSuspended());
		c.setSuspensionReason(cert.getSuspensionReason());
		
		return c;
		
	}
}
