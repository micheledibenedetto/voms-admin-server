package org.glite.security.voms.admin.request.criteria;

import org.glite.security.voms.admin.persistence.model.request.Request;

public class RequestStatusExpression implements RequestMatchCriterion {

	Request.STATUS status;
	
	public RequestStatusExpression(Request.STATUS s) {
		status = s;
	}
	
	@Override
	public boolean matches(Request req) {
		return req.getStatus().equals(status);
	}

}
