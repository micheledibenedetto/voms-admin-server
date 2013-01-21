package org.glite.security.voms.admin.request.criteria;

import org.glite.security.voms.admin.persistence.model.request.Request;

public class RequesterInstitutionExpression implements RequestMatchCriterion {

	final String institution;
	
	public RequesterInstitutionExpression(String institution) {
		this.institution = institution;
	}
	
	@Override
	public boolean matches(Request req) {
		return req.getRequesterInfo().getInstitution().equals(institution);
	}

	@Override
	public String toString() {
		return "RequesterInstitutionExpression [institution=" + institution + "]";
	}

}
