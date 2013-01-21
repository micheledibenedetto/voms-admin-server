package org.glite.security.voms.admin.request;

import java.util.Set;

import org.glite.security.voms.admin.persistence.model.VOMSAdmin;
import org.glite.security.voms.admin.persistence.model.request.Request;

/**
 * An {@link AdministratorRequestMapper} maps a request that requires administrator
 * approval to a set of administrators that can handle such request
 * 
 *
 */
public interface AdministratorRequestMapper {
	
	/**
	 * Maps a request to a set of administrators that can handle such request
	 * 
	 * @param request the incoming request
	 * @return a possibly empty set of {@link VOMSAdmin} administrators
	 */
	public Set<VOMSAdmin> resolveAdministrators(Request request);

}
