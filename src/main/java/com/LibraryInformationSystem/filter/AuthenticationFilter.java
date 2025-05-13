package com.LibraryInformationSystem.filter;

import jakarta.servlet.Filter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.LibraryInformationSystem.util.CookieUtil;
import com.LibraryInformationSystem.util.SessionUtil;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter extends HttpFilter implements Filter {
	
	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String DASHBOARD = "/dashboard";
	private static final String ABOUT = "/about";
	private static final String PROFILE = "/profile";
	private static final String CONTACT = "/contact";
	private static final String BROWSE = "/browse";
	private static final String LOGOUT = "/logout";
  
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthenticationFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();

		// Allow access to resources
				if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".css")) {
					chain.doFilter(request, response);
					return;
				}
				
				/*boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;*/
				String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue()
						: null;
				
				if ("admin".equals(userRole)) {
					// Admin is logged in
					if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(ROOT)) {
						res.sendRedirect(req.getContextPath() + DASHBOARD);
					} else if (uri.endsWith(DASHBOARD) || uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(LOGOUT) || uri.endsWith(CONTACT) || uri.endsWith(PROFILE)) {
						chain.doFilter(request, response);
					} else if (uri.endsWith(BROWSE)) {
						res.sendRedirect(req.getContextPath() + DASHBOARD);
					} else {
						res.sendRedirect(req.getContextPath() + DASHBOARD);
					}
				} else if ("customer".equals(userRole)) {
					// User is logged in
					if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(ROOT)) {
						res.sendRedirect(req.getContextPath() + HOME);
					} else if (uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(ABOUT) || uri.endsWith(PROFILE)
							|| uri.endsWith(CONTACT) || uri.endsWith(LOGOUT)) {
						chain.doFilter(request, response);
					} else if (uri.endsWith(DASHBOARD)) {
						res.sendRedirect(req.getContextPath() + HOME);
					} else {
						res.sendRedirect(req.getContextPath() + HOME);
					}
				} else {
					// Not logged in
					if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(ROOT)) {
						chain.doFilter(request, response);
					} else {
						res.sendRedirect(req.getContextPath() + LOGIN);
					}
				}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
