package org.glite.security.voms.admin.request;

import java.util.HashSet;
import java.util.Set;

import org.glite.security.voms.admin.persistence.model.request.Request;
import org.glite.security.voms.admin.request.criteria.RequestMatchCriterion;

public class BaseRequestMatcher implements RequestMatcher {

	Set<RequestMatchCriterion> criteria;
	
	public BaseRequestMatcher() {
		criteria = new HashSet<RequestMatchCriterion>();
	}
	
	public BaseRequestMatcher(Set<RequestMatchCriterion> c) {
		criteria = c;
	}
	
	@Override
	public boolean matchesRequest(Request r) {
		
		for (RequestMatchCriterion c: criteria){
			
			if (!c.matches(r))
				return false;
			
		}
		
		return true;
	}

	public void addCriteria(RequestMatchCriterion c){
		criteria.add(c);
	}
	
	
}
