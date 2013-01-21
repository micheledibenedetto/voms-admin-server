package org.glite.security.voms.admin.request.admin_lookup;

import java.util.Collections;
import java.util.Set;

import org.glite.security.voms.admin.persistence.dao.VOMSGroupDAO;
import org.glite.security.voms.admin.persistence.dao.VOMSRoleDAO;
import org.glite.security.voms.admin.persistence.model.VOMSAdmin;
import org.glite.security.voms.admin.persistence.model.VOMSGroup;
import org.glite.security.voms.admin.persistence.model.VOMSRole;
import org.glite.security.voms.admin.persistence.model.request.Request;
import org.glite.security.voms.admin.request.AdministratorLookupStrategy;

public class ClosestGroupManagerFromRequestContext implements AdministratorLookupStrategy {

	
	@Override
	public Set<VOMSAdmin> lookupAdministrators(Request r) {
		String group = r.getContext();
		
		if (group == null)
			return Collections.emptySet();
		
		Set<VOMSAdmin> result = Collections.emptySet();
		VOMSGroup leafGroup = VOMSGroupDAO.instance().findByName(group);
		VOMSRole groupManagersRole = VOMSRoleDAO.instance().getGroupManagerRole();
		
		// TO BE finished		
		
		return null;
	}

}
