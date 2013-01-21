package org.glite.security.voms.admin.request.criteria;

import org.glite.security.voms.admin.persistence.model.request.NewVOMembershipRequest;
import org.glite.security.voms.admin.persistence.model.request.Request;

public class RequestedLeafGroupExpression implements RequestMatchCriterion {

	public String groupName;
	
	@Override
	public boolean matches(Request req) {
		
		if (req instanceof NewVOMembershipRequest){
			
			NewVOMembershipRequest voMembReq = (NewVOMembershipRequest) req;
			
			if (voMembReq.getLowestRankRequestedGroup() != null)
				return groupName.equals(voMembReq.getLowestRankRequestedGroup());
		}
		
		return false;
	}

}
