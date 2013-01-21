package org.glite.security.voms.admin.request.criteria;

import org.glite.security.voms.admin.persistence.model.request.Request;

/**
 * 
 *
 */
public interface RequestMatchCriterion {

	/**
	 * 
	 * @param req
	 * @return
	 */
	public boolean matches(Request req);
	
}
