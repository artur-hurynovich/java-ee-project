package com.hurynovich.mus_site.controller.filter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.hurynovich.mus_site.bean.user.UserRole;
import com.hurynovich.mus_site.util.AuthsXMLHandler;

public class AuthMapping {
	private static AuthMapping INSTANCE;
	
	private Map<String, List<UserRole>> map;
	
	private AuthMapping(String xmlPath) throws ParserConfigurationException, 
		SAXException, IOException {
		fillMap(xmlPath);
	}
	
	public static AuthMapping getInstance(String xmlPath) throws ParserConfigurationException, 
		SAXException, IOException {
		if (INSTANCE == null) {
			INSTANCE = new AuthMapping(xmlPath);
		}
		return INSTANCE;
	}
	
	public List<String> getAllUrls() {
		if (map == null) {
			return null;
		} else {
			List<String> urls = new LinkedList<>(map.keySet());
			return urls;
		}
	}
	
	public List<UserRole> getRolesForUrl(String url) {
		if (map == null) {
			return null;
		} else {
			List<UserRole> roles = map.get(url);
			return roles;
		}
	}
	
	private void fillMap(String xmlPath) throws ParserConfigurationException, 
		SAXException, IOException {
		AuthsXMLHandler xmlHandler = new AuthsXMLHandler(xmlPath);
		map = xmlHandler.getAuths();
	}
}
