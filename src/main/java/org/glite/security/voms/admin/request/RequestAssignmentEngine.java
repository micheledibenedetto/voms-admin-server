package org.glite.security.voms.admin.request;

import java.util.Collections;
import java.util.Set;

import org.glite.security.voms.admin.persistence.model.VOMSAdmin;
import org.glite.security.voms.admin.persistence.model.request.Request;

/**
 * Maps a request to a set of administrators eligible to handle it.
 * A set of {@link RequestAssignmentPolicy} drive the decision process.
 * 
 * The request mapper is a singleton. It needs to be initialized calling
 * the {@link #initialize(Set)} method passing the set of policies that drive the mapping process 
 * before the {@link #resolveAdministrators(Request)}
 * 
 * @author cecco
 *
 */
public class RequestAssignmentEngine implements AdministratorRequestMapper{

	public static volatile RequestAssignmentEngine instance = null;
	
	private Set<RequestAssignmentPolicy> policies;
	
	private RequestAssignmentEngine(Set<RequestAssignmentPolicy> policies) {
		this.policies = policies;
	}
	
	@Override
	public synchronized Set<VOMSAdmin> resolveAdministrators(Request request) {
		
		if (policies == null)
			return Collections.emptySet();
		
		for (RequestAssignmentPolicy p: policies){	
			if (p.getRequestMatcher().matchesRequest(request))
				return p.getAdministratorLookupStrategy().lookupAdministrators(request);
		}
		
		return Collections.emptySet();
	}

	/**
	 * Returns an instance of the request mapper
	 * @return
	 * @throws IllegalStateException if the {@link RequestAssignmentEngine} was not initialized.
	 */
	public static RequestAssignmentEngine instance(){
		if (instance == null)
			throw new IllegalStateException("Please initialize request mapper before trying to initialize it!");
		return instance;
	}
	
	public static synchronized void initialize(Set<RequestAssignmentPolicy> policies){
		if (instance == null)
			instance = new RequestAssignmentEngine(policies);
		else
			instance.policies = policies;
	}
}
