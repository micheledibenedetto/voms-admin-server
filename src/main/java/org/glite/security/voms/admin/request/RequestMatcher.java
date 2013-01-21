package org.glite.security.voms.admin.request;

import org.glite.security.voms.admin.persistence.model.request.Request;

/**
 * A {@link RequestMatcher} provides a way to check wheter the policy applies 
 * to a given request.
 *  
 * @author cecco
 *
 */
public interface RequestMatcher {
	
	/**
	 * Checks wheter this policy matches a given request
	 * @param r the request to be checked
	 * @return <code>true</code> if the policy applies to request, <code>false</code> otherwise
	 */
	public boolean matchesRequest(Request r);
	
}
