package org.glite.security.voms.admin.request;

/**
 * An administrator resolve policy binds together a {@link RequestMatcher}
 * and a {@link AdministratorLookupStrategy}
 * 
 * 
 *
 */
public final class RequestAssignmentPolicy {

	final RequestMatcher requestMatcher;
	final AdministratorLookupStrategy administratorLookupStrategy;
	
	
	public RequestAssignmentPolicy(RequestMatcher m, AdministratorLookupStrategy s) {
		this.requestMatcher = m;
		this.administratorLookupStrategy = s;
	
	}
	
	public RequestMatcher getRequestMatcher() {
		return requestMatcher;
	}


	public AdministratorLookupStrategy getAdministratorLookupStrategy() {
		return administratorLookupStrategy;
	}
	
	
}
