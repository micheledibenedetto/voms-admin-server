package org.glite.security.voms.admin.request.criteria;

import org.glite.security.voms.admin.persistence.model.request.Request;

public class RequestTypeExpression implements RequestMatchCriterion {

	Class<?> requestType;
	
	public RequestTypeExpression(Class<?> type) {
		requestType = type;
	}
	
	@Override
	public boolean matches(Request req) {
		return (req.getClass().equals(requestType));
	}

}
