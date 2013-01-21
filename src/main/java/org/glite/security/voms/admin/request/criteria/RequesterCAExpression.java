package org.glite.security.voms.admin.request.criteria;

import org.glite.security.voms.admin.persistence.model.request.Request;

public class RequesterCAExpression implements RequestMatchCriterion {

	public final String caSubject;
	
	public RequesterCAExpression(String caSubject) {
		this.caSubject = caSubject;
	}
	
	@Override
	public boolean matches(Request req) {
		return req.getRequesterInfo().getCertificateIssuer().equals(caSubject);
	}

	@Override
	public String toString() {
		return "RequesterCAExpression [caSubject=" + caSubject + "]";
	}
	

}
