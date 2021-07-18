package com.hkk.cloudtv.core.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 */
public class MyAccessControlFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        System.out.println("isAccessAllowed");
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            // return subject.getPrincipal() != null && (subject.isRemembered() || subject.isAuthenticated());
            return subject.getPrincipal() != null && subject.isAuthenticated();

        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("onAccessDenied");
        return false;
    }
}
