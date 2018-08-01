package com.hurynovich.mus_site.controller.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class QueryStringCatcher implements Filter {
	private StringBuilder queryBuilder = new StringBuilder();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		// getting parameters from both GET and POST requests
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Enumeration<String> queryParamsNames = request.getParameterNames();
		while (queryParamsNames.hasMoreElements()) {
			String queryParamName = queryParamsNames.nextElement();
			String queryParamValue = request.getParameter(queryParamName);
			queryBuilder.append(queryParamName + "=" + queryParamValue + "&");
		}
		String previousQuery = queryBuilder.toString();
		
		int builderLength = queryBuilder.length();
		queryBuilder.delete(0, builderLength);
		
		if (previousQuery != null && previousQuery.contains("_page")
			&& !previousQuery.contains("switch_lang")) {
			httpRequest.getSession().setAttribute("previousQuery", previousQuery);
		}
		chain.doFilter(httpRequest, response);
	}
}
