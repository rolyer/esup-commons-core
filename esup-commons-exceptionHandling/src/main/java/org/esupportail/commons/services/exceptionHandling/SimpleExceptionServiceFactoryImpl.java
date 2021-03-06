/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.exceptionHandling;

import java.util.HashMap;
import java.util.Map;

import org.esupportail.commons.beans.AbstractApplicationAwareBean;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.authentication.AuthenticationService;

import org.springframework.util.StringUtils;

/**
 * A factory that returns SimpleExceptionService.
 *
 * See /properties/exceptionHandling/exceptionHandling-example.xml.
 */
public class SimpleExceptionServiceFactoryImpl
extends AbstractApplicationAwareBean
implements ExceptionServiceFactory {

	/**
	 * A log level.
	 */
	static final String DEBUG = "debug";

	/**
	 * A log level.
	 */
	static final String TRACE = "trace";

	/**
	 * A log level.
	 */
	static final String INFO = "info";

	/**
	 * A log level.
	 */
	static final String WARN = "warn";

	/**
	 * A log level.
	 */
	static final String ERROR = "error";

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2883836943763597406L;

	/**
	 * The default forward view.
	 */
	private static final String DEFAULT_EXCEPTION_VIEW = "/stylesheets/exception.jsp";

//	/**
//	 * A logger.
//	 */
//	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The log level.
	 */
	private String logLevel;

	/**
	 * The exception views.
	 */
	private Map<Class<? extends Throwable>, String> exceptionViews;

	/**
	 * The authentication service.
	 */
	private AuthenticationService authenticationService;

	/**
	 * Constructor.
	 */
	public SimpleExceptionServiceFactoryImpl() {
		super();
		exceptionViews = new HashMap<Class<? extends Throwable>, String>();
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (!exceptionViews.containsKey(Exception.class)) {
			setExceptionView(DEFAULT_EXCEPTION_VIEW);
		}
		if (!StringUtils.hasText(logLevel)) {
			logLevel = ERROR;
		}
		if (!ERROR.equalsIgnoreCase(logLevel)
				&& !WARN.equalsIgnoreCase(logLevel)
				&& !INFO.equalsIgnoreCase(logLevel)
				&& !TRACE.equalsIgnoreCase(logLevel)
				&& !DEBUG.equalsIgnoreCase(logLevel)) {
			throw new ConfigException("invalid value for property [logLevel], "
					+ "accepted values are ERROR, WARN, INFO, TRACE and DEBUG");
		}
	}

	@Override
    public ExceptionService getExceptionService() {
		return new SimpleExceptionServiceImpl(
				getI18nService(), getApplicationService(),
				exceptionViews, authenticationService, logLevel);
	}

	/**
	 * @return the authenticationService
	 */
	protected AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	/**
	 * @param authenticationService the authenticationService to set
	 */
	public void setAuthenticationService(
			final AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	/**
	 * Add an exception view.
	 * @param className
	 * @param exceptionView
	 */
	@SuppressWarnings("unchecked")
    private void addExceptionView(final String className, final String exceptionView) {
		try {
			Class<?> clazz = Class.forName(className);
			if (!(Throwable.class.isAssignableFrom(clazz))) {
				throw new ConfigException("class [" + className
						+ "] is not a subclass of [" + Throwable.class + "]");
			}
			Class <? extends Throwable> exceptionClazz = (Class <? extends Throwable>) clazz;
			this.exceptionViews.put(exceptionClazz, exceptionView);
		} catch (ClassNotFoundException e) {
			throw new ConfigException(e);
		}
	}

	/**
	 * @param exceptionViews the exceptionViews to set
	 */
	public void setExceptionViews(final Map<String, String> exceptionViews) {
		for (String className : exceptionViews.keySet()) {
			addExceptionView(className, exceptionViews.get(className));
		}
	}

	/**
	 * @param exceptionView the exceptionView to set
	 */
	public void setExceptionView(final String exceptionView) {
		exceptionViews.put(Exception.class, exceptionView);
	}

	/**
	 * @return the exceptionView
	 */
	protected Map<Class<? extends Throwable>, String> getExceptionViews() {
		return exceptionViews;
	}

	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(final String logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * @return the logLevel
	 */
	protected String getLogLevel() {
		return logLevel;
	}

}
