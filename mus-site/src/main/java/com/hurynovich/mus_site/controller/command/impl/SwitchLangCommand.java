package com.hurynovich.mus_site.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;

public class SwitchLangCommand implements ICommand {
	private String lang;

	@Override
	public String execute(HttpServletRequest request) {
		lang = request.getParameter("lang");
		request.getSession().setAttribute("lang", lang);
		
		Object previousQuery = request.getSession().getAttribute("previousQuery");
		if (previousQuery == null) {
			previousQuery = "command=reviews_page";
		}
		
		return "/controller?" + previousQuery;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}
