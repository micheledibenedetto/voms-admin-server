package org.glite.security.voms.admin.request.admin_lookup;

import java.util.Collections;
import java.util.Set;

import org.glite.security.voms.admin.persistence.model.VOMSAdmin;
import org.glite.security.voms.admin.persistence.model.request.Request;
import org.glite.security.voms.admin.request.AdministratorLookupStrategy;

public class NamedAdminLookupStrategy implements AdministratorLookupStrategy {

	VOMSAdmin admin;
	
	public NamedAdminLookupStrategy(VOMSAdmin a) {
		this.admin = a;
	}
	
	@Override
	public Set<VOMSAdmin> lookupAdministrators(Request r) {
		return Collections.singleton(admin);
	}

}
