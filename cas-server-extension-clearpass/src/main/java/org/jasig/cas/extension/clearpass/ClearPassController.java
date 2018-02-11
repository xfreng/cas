package org.jasig.cas.extension.clearpass;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.ticket.registry.TicketRegistrySupport;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * A controller that returns the password based on some external authentication/authorization rules.  The recommended
 * method is to use the Apereo CAS Client for Java and its proxy authentication features.
 *
 * @deprecated As of 4.1, use {@link org.jasig.cas.authentication.CacheCredentialsMetaDataPopulator} instead.
 * @author Scott Battaglia
 * @since 1.0
 */
@Deprecated
public final class ClearPassController extends AbstractController {

    /** view if clearpass request fails. */
    protected static final String DEFAULT_SERVICE_FAILURE_VIEW_NAME = "protocol/clearPass/clearPassFailure";

    /** view if clearpass request succeeds. */
    protected static final String DEFAULT_SERVICE_SUCCESS_VIEW_NAME = "protocol/clearPass/clearPassSuccess";

    /** key under which clearpass will be placed into the model. */
    protected static final String MODEL_CLEARPASS = "credentials";

    /** key under which failure descriptions are placed into the model. */
    protected static final String MODEL_FAILURE_DESCRIPTION = "description";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearPassController.class);

    @NotNull
    private String successView = DEFAULT_SERVICE_SUCCESS_VIEW_NAME;

    @NotNull
    private String failureView = DEFAULT_SERVICE_FAILURE_VIEW_NAME;

    @NotNull
    private final Map<String, String> credentialsCache;

    @Autowired
    @Qualifier("defaultTicketRegistrySupport")
    private TicketRegistrySupport ticketRegistrySupport;

    @Autowired
    @Qualifier("ticketGrantingTicketCookieGenerator")
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

    /**
     * Instantiates a new clear pass controller.
     *
     * @param credentialsCache the credentials cache
     */
    public ClearPassController(final Map<String, String> credentialsCache) {
        this.credentialsCache = credentialsCache;
    }

    @Override
    public ModelAndView handleRequestInternal(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        String userName = request.getRemoteUser();
        if (StringUtils.isBlank(userName)) {
            LOGGER.debug("Username could not be detected from the request.");
            final String ticketGrantingTicket = ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
            if (StringUtils.isBlank(ticketGrantingTicket)) {
                return returnError("No ticketGrantingTicket could be detected.");
            }
            LOGGER.debug("Found ticket-granting ticket id in the request", ticketGrantingTicket);
            final Principal p = this.ticketRegistrySupport.getAuthenticatedPrincipalFrom(ticketGrantingTicket);
            if (p == null) {
                return returnError("No principal could be detected from the ticket-granting ticket.");
            }
            userName = p.getId();
        }
        LOGGER.debug("Handling clearPass request for user [{}]", userName);

        if (StringUtils.isBlank(userName)) {
            return returnError("No username was provided to clearPass.");
        }

        if (!this.credentialsCache.containsKey(userName)) {
            return returnError("Password could not be found in cache for user " + userName);
        }

        final String password = this.credentialsCache.get(userName);
        if (StringUtils.isBlank(password)) {
            return returnError("Password is null or blank");
        }

        LOGGER.debug("Retrieved credentials will be provided to the requesting service.");
        return new ModelAndView(this.successView, MODEL_CLEARPASS, password);
    }

    /**
     * Return error based on {@link #setFailureView(String)}.
     *
     * @param description the description
     * @return the model and view
     */
    protected ModelAndView returnError(final String description) {
        final ModelAndView mv = new ModelAndView(this.failureView);
        mv.addObject(MODEL_FAILURE_DESCRIPTION, description);
        return mv;
    }

    public void setTicketRegistrySupport(final TicketRegistrySupport ticketRegistrySupport) {
        this.ticketRegistrySupport = ticketRegistrySupport;
    }

    public void setTicketGrantingTicketCookieGenerator(final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
        this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
    }

    public void setSuccessView(final String successView) {
        this.successView = successView;
    }

    public void setFailureView(final String failureView) {
        this.failureView = failureView;
    }
}
