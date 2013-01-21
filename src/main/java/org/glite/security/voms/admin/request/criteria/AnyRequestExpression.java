package org.glite.security.voms.admin.request.criteria;

import org.glite.security.voms.admin.persistence.model.request.Request;

/**
 * This expression matches any request
 *
 */
public class AnyRequestExpression implements RequestMatchCriterion {

	@Override
	public boolean matches(Request req) {
		
		return true;
	}

}
