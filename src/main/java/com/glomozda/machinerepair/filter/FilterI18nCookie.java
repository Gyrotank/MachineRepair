package com.glomozda.machinerepair.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Transfer the value of user Locale into LocaleContextHolder which is used by spring-security
 */
public class FilterI18nCookie implements Filter {
    
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(FilterI18nCookie.class);

    private WebApplicationContext springContext;
    
    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pFilterChain) throws IOException, ServletException {
        if (!(pRequest instanceof HttpServletRequest)) {
            pFilterChain.doFilter(pRequest, pResponse);
            return;
        }

//        HttpServletRequest request = (HttpServletRequest) pRequest;
//
//        Cookie cookie = WebUtils.getCookie(request, 
//        		CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME);
//        if (cookie != null) {
//            Locale locale = org.springframework.util.StringUtils
//            		.parseLocaleString(cookie.getValue());
//            if (locale != null) {
//                LOG.info("Locale cookie: [" + cookie.getValue() + "] == '" + locale + "'");
//                request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, locale);
//                LocaleContextHolder.setLocale(locale, true);
//            }
//        }
        
        LocaleResolver bean = springContext.getBean(LocaleResolver.class);
        Locale locale = bean.resolveLocale((HttpServletRequest) pRequest);
//        LOG.info("Locale -> " + locale);
        LocaleContextHolder.setLocale(locale, true);

        pFilterChain.doFilter(pRequest, pResponse);
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    	springContext = 
    			WebApplicationContextUtils
    			.getWebApplicationContext(filterConfig.getServletContext());
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

}