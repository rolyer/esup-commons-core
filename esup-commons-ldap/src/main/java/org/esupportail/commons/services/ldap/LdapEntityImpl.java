/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A basic implementation of LdapEntity.
 */
public class LdapEntityImpl implements LdapEntity {

	/**
	 * The id for serialization.
	 */
	private static final long serialVersionUID = 5921570970584589329L;

	/**
	 * The identifier of the LDAP user.
	 */
	private String id;

	/**
	 * The attributes of the LDAP user.
	 */
	private Map<String, List<String>> attributes = new HashMap<String, List<String>>();

	/**
	 * Constructor.
	 */
	public LdapEntityImpl() {
		super();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode() + "["
		+ "id=[" + id + "], "
		+ "attributeNames=" + getAttributeNames() + ", "
		+ "attributes=" + attributes
		+ "]";
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(final String id) {
		this.id = id;
	}

	@Override
	public Map<String, List<String>> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(
			final Map<String, List<String>> attributes) {
		this.attributes = attributes;
	}

	@Override
	public List<String> getAttributeNames() {
		return new ArrayList<String>(attributes.keySet());
	}

	@Override
	public List<String> getAttributes(final String name) {
		List<String> result = attributes.get(name);
		if (result == null) {
			result = new ArrayList<String>();
		}
		return result;
	}

	@Override
	public String getAttribute(final String name) {
		List<String> values = getAttributes(name);
		if (values.size() < 1) {
			return null;
		}
		return values.get(0);
	}

}
