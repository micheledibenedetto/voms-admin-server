package org.glite.security.voms.admin.request.admin_lookup;

import java.util.Collections;
import java.util.Set;

import org.glite.security.voms.admin.operations.VOMSPermission;
import org.glite.security.voms.admin.persistence.dao.VOMSGroupDAO;
import org.glite.security.voms.admin.persistence.model.VOMSAdmin;
import org.glite.security.voms.admin.persistence.model.VOMSGroup;
import org.glite.security.voms.admin.persistence.model.request.GroupMembershipRequest;
import org.glite.security.voms.admin.persistence.model.request.NewVOMembershipRequest;
import org.glite.security.voms.admin.persistence.model.request.Request;
import org.glite.security.voms.admin.persistence.model.request.RoleMembershipRequest;
import org.glite.security.voms.admin.request.AdministratorLookupStrategy;

public class AuthorizedAdminsInRequestContext implements AdministratorLookupStrategy {

	
	private Set<VOMSAdmin> getAdminsWithPermissions(String groupName){
		if (groupName == null)
			return Collections.emptySet();
		
		VOMSGroup g = VOMSGroupDAO.instance().findByName(groupName);
		
		if (g == null)
			return Collections.emptySet();
		
		return g.getACL().getAdminsWithPermissions(VOMSPermission.getRequestsRWPermissions());
	}
	
	@Override
	public Set<VOMSAdmin> lookupAdministrators(Request r) {
		
		if (r instanceof NewVOMembershipRequest){
			
			NewVOMembershipRequest req = (NewVOMembershipRequest)r;
			return getAdminsWithPermissions(req.getLowestRankRequestedGroup());
		}
		
		if (r instanceof GroupMembershipRequest){
			
			GroupMembershipRequest req = (GroupMembershipRequest)r;
			return getAdminsWithPermissions(req.getGroupName());			

		}
		
		if (r instanceof RoleMembershipRequest){
			RoleMembershipRequest req = (RoleMembershipRequest)r;
			return getAdminsWithPermissions(req.getGroupName());
			
		}
		
		return Collections.emptySet();
	}

}
