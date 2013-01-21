package org.glite.security.voms.admin.request;

import java.util.Set;

import org.glite.security.voms.admin.persistence.model.VOMSAdmin;
import org.glite.security.voms.admin.persistence.model.request.Request;

/**
 * An {@link AdministratorLookupStrategy} maps a
 * request to a set of administrators that are eligible to handle it 
 * 
 *
 */
public interface AdministratorLookupStrategy {

	/**
	 * Finds a set of administrators that are eligible to handle a request
	 * @param r the request
	 * @return a possibly empty set of {@link VOMSAdmin} objects
	 */
	Set<VOMSAdmin> lookupAdministrators(Request r);
	
}
